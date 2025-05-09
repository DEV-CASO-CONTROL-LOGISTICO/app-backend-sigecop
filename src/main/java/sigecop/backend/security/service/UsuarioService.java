/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.service;

import sigecop.backend.master.model.Proveedor;
import sigecop.backend.master.repository.ProveedorRepository;
import sigecop.backend.security.dto.PaginaResponse;
import sigecop.backend.security.dto.UsuarioRequest;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.security.model.Rol;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.repository.PaginaRepository;
import sigecop.backend.security.repository.RolRepository;
import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.Encrypt;
import java.util.List;
import java.util.Optional;

import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Moises_F16.7.24
 */
@Service
public class UsuarioService extends ServiceGeneric<UsuarioResponse, UsuarioRequest, Usuario> {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    PaginaRepository paginaRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    ProveedorRepository proveedorRepository;

    public UsuarioService(UsuarioRepository _usuarioRepository) {
        super(UsuarioResponse.class,_usuarioRepository);
        this.usuarioRepository = _usuarioRepository;
    }

    public UsuarioResponse searchForCredentials(UsuarioRequest request) {
        return UsuarioResponse.fromEntity(
                usuarioRepository.getUserForCredentials(
                        request.getCuenta(),
                        Encrypt.hashClave(request.getClave())
                ),UsuarioResponse.class);
    }

    public UsuarioResponse searchInfoForId(Integer usuarioId) {
        Optional<Usuario> result = usuarioRepository.findById(usuarioId);
        if (!result.isPresent()) {
            return null;
        }
        UsuarioResponse usuarioResponse = UsuarioResponse.fromEntity(result.get(),UsuarioResponse.class);
        usuarioResponse.setPaginas(PaginaResponse.fromEntities(paginaRepository.listForRol(usuarioResponse.getRol().getId())));
        return usuarioResponse;
    }

    @Override
    public List<Usuario> listBase(UsuarioRequest filter) {
        return usuarioRepository.findByFilter(
                filter.getNombre(),
                filter.getApellidoPaterno(),
                filter.getApellidoMaterno(),
                filter.getRolId()
        );
    }

    @Override
    public ObjectResponse<Usuario> recordToEntityEdit(Usuario entity, UsuarioRequest request) {
        Rol rol;
        Proveedor proveedor=null;
        Optional<Rol> rolOptional = rolRepository.findById(request.getRolId());
        if (rolOptional.isPresent()) {
            rol=rolOptional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró el rol ingresado",
                    null);
        }
        if(request.getProveedorId()!=null ){
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(request.getProveedorId());
            if (proveedorOptional.isPresent()) {
                proveedor=proveedorOptional.get();
            }else{
                return new ObjectResponse(
                        Boolean.FALSE,
                        "No se encontró el proveedor ingresado",
                        null);
            }
        }
        entity.setRol(rol);
        entity.setProveedor(proveedor);
        entity.setNombre(request.getNombre());
        entity.setApellidoPaterno(request.getApellidoPaterno());
        entity.setApellidoMaterno(request.getApellidoMaterno());
        if (request.getUpdatePassword() != null && request.getUpdatePassword()) {
            entity.setClave(Encrypt.hashClave(request.getClave()));
        }
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Usuario> recordToEntityNew(UsuarioRequest request) {

        System.out.println("-----------------UsuarioService save");
        System.out.println(request);
        Rol rol;
        Proveedor proveedor=null;
        Optional<Rol> rolOptional = rolRepository.findById(request.getRolId());
        if (rolOptional.isPresent()) {
            rol=rolOptional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró el rol ingresado",
                    null);
        }
        if(request.getProveedorId()!=null ){
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(request.getProveedorId());
            if (proveedorOptional.isPresent()) {
                proveedor=proveedorOptional.get();
            }else{
                return new ObjectResponse(
                        Boolean.FALSE,
                        "No se encontró el proveedor ingresado",
                        null);
            }
        }
        return new ObjectResponse<>(Boolean.TRUE,null,new Usuario(
                request.getId(),
                rol,
                proveedor,
                request.getNombre(),
                request.getApellidoPaterno(),
                request.getApellidoMaterno(),
                request.getCuenta(),
                Encrypt.hashClave(request.getClave())
        ));
    }

}
