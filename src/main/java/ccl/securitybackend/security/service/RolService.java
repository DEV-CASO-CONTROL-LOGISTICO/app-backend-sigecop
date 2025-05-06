/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.service;

import ccl.securitybackend.master.model.Proveedor;
import ccl.securitybackend.master.repository.ProveedorRepository;
import ccl.securitybackend.security.dto.RolRequest;
import ccl.securitybackend.security.dto.RolResponse;
import ccl.securitybackend.security.dto.UsuarioRequest;
import ccl.securitybackend.security.dto.UsuarioResponse;
import ccl.securitybackend.security.model.Rol;
import ccl.securitybackend.security.model.Usuario;
import ccl.securitybackend.security.repository.PaginaRepository;
import ccl.securitybackend.security.repository.RolRepository;
import java.util.List;
import java.util.Optional;

import ccl.securitybackend.security.repository.UsuarioRepository;
import ccl.securitybackend.utils.Encrypt;
import ccl.securitybackend.utils.ObjectResponse;
import ccl.securitybackend.utils.generic.ServiceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Moises_F16.7.24
 */
@Service
public class RolService extends ServiceGeneric<RolResponse, RolRequest, Rol> {

    private final RolRepository rolRepository;

    public RolService(RolRepository _rolRepository) {
        super(RolResponse.class,_rolRepository);
        this.rolRepository = _rolRepository;
    }

    @Override
    public List<Rol> listBase(RolRequest filter) {
        return rolRepository.findByFilter();
    }

    @Override
    public ObjectResponse<Rol> recordToEntityEdit(Rol entity, RolRequest request) {
        entity.setNombre(request.getNombre());
        entity.setCodigo(request.getCodigo());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Rol> recordToEntityNew(RolRequest request) {
        return new ObjectResponse<>(Boolean.TRUE,null,new Rol(
                request.getId(),
                request.getCodigo(),
                request.getNombre()
        ));
    }


}
