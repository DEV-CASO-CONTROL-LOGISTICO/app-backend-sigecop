/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.service;

import ccl.securitybackend.master.model.Proveedor;
import ccl.securitybackend.master.repository.ProveedorRepository;
import ccl.securitybackend.security.dto.PaginaResponse;
import ccl.securitybackend.security.dto.UsuarioRequest;
import ccl.securitybackend.security.dto.UsuarioResponse;
import ccl.securitybackend.security.model.Rol;
import ccl.securitybackend.security.model.Usuario;
import ccl.securitybackend.security.repository.PaginaRepository;
import ccl.securitybackend.security.repository.RolRepository;
import ccl.securitybackend.security.repository.UsuarioRepository;
import ccl.securitybackend.utils.Encrypt;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Moises_F16.7.24
 */
@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PaginaRepository paginaRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    ProveedorRepository proveedorRepository;

    public UsuarioResponse searchForCredentials(UsuarioRequest request) {
        return UsuarioResponse.fromEntity(
                usuarioRepository.getUserForCredentials(
                        request.getCuenta(),
                        Encrypt.hashClave(request.getClave())
                ));
    }

    public UsuarioResponse searchForId(Integer usuarioId) {
        Optional<Usuario> result = usuarioRepository.findById(usuarioId);
        if (!result.isPresent()) {
            return null;
        }
        UsuarioResponse usuarioResponse = UsuarioResponse.fromEntity(result.get());
        usuarioResponse.setPaginas(PaginaResponse.fromEntities(paginaRepository.listForRol(usuarioResponse.getRol().getId())));
        return usuarioResponse;
    }

    public UsuarioResponse find(Integer id) {
        Optional<Usuario> result = usuarioRepository.findById(id);
        if (!result.isPresent()) {
            return null;
        }
        return UsuarioResponse.fromEntity(result.get());
    }

    public List<UsuarioResponse> list(UsuarioRequest filter) {
        return UsuarioResponse.fromEntities(usuarioRepository.findByFilter(filter.getNombre(), filter.getApellidoPaterno(), filter.getApellidoMaterno(), filter.getRolId()));
    }

    public UsuarioResponse save(UsuarioRequest usuarioRequest) {
        Rol rol;
        Proveedor proveedor=null;

        Optional<Rol> rolOptional = rolRepository.findById(usuarioRequest.getRolId());
        if (rolOptional.isPresent()) {
            rol=rolOptional.get();
        }else{
            return null;
        }

        if(usuarioRequest.getProveedorId()!=null ){
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(usuarioRequest.getProveedorId());
            if (proveedorOptional.isPresent()) {
                proveedor=proveedorOptional.get();
            }else{
                return null;
            }
        }

        Usuario user;
        if (usuarioRequest.getId() != null) {
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioRequest.getId());
            if (optionalUsuario.isEmpty()) {
                return null;
            }
            user = optionalUsuario.get();
            user.setRol(rol);
            user.setNombre(usuarioRequest.getNombre());
            user.setApellidoPaterno(usuarioRequest.getApellidoPaterno());
            user.setApellidoMaterno(usuarioRequest.getApellidoMaterno());
            if (usuarioRequest.getUpdatePassword() != null && usuarioRequest.getUpdatePassword()) {
                user.setClave(Encrypt.hashClave(usuarioRequest.getClave()));
            }
        } else {
            user = new Usuario(
                    usuarioRequest.getId(),
                    rol,
                    proveedor,
                    usuarioRequest.getNombre(),
                    usuarioRequest.getApellidoPaterno(),
                    usuarioRequest.getApellidoMaterno(),
                    usuarioRequest.getCuenta(),
                    Encrypt.hashClave(usuarioRequest.getClave())
            );
        }
        user = usuarioRepository.save(user);
        return UsuarioResponse.fromEntity(user);
    }

    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
