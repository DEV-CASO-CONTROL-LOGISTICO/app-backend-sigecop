/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.service;

import sigecop.backend.security.dto.RolRequest;
import sigecop.backend.security.dto.RolResponse;
import sigecop.backend.security.model.Rol;
import sigecop.backend.security.repository.RolRepository;
import java.util.List;

import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
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
