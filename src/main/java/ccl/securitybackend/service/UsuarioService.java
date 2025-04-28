/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.service;

import ccl.securitybackend.dto.PaginaResponse;
import ccl.securitybackend.dto.UsuarioRequest;
import ccl.securitybackend.dto.UsuarioResponse;
import ccl.securitybackend.model.Rol;
import ccl.securitybackend.model.Usuario;
import ccl.securitybackend.repository.PaginaRepository;
import ccl.securitybackend.repository.RolRepository;
import ccl.securitybackend.repository.UsuarioRepository;
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
        Rol rol = rolRepository.findById(usuarioRequest.getRolId()).get();
        if (rol == null) {
            return null;
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
            user.setEmpresa(usuarioRequest.getEmpresa());
            user.setCorreo(usuarioRequest.getCorreo());
            if (usuarioRequest.getUpdatePassword() != null && usuarioRequest.getUpdatePassword()) {
                user.setClave(Encrypt.hashClave(usuarioRequest.getClave()));
            }
        } else {
            user = new Usuario(
                    usuarioRequest.getId(),
                    rol,
                    usuarioRequest.getNombre(),
                    usuarioRequest.getApellidoPaterno(),
                    usuarioRequest.getApellidoMaterno(),
                    usuarioRequest.getEmpresa(),
                    usuarioRequest.getCorreo(),
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
