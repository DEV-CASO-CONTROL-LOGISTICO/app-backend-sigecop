package sigecop.backend.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.security.dto.PaginaRequest;
import sigecop.backend.security.dto.PaginaResponse;
import sigecop.backend.security.dto.PermisoRequest;
import sigecop.backend.security.dto.PermisoResponse;
import sigecop.backend.security.model.Pagina;
import sigecop.backend.security.model.Permiso;
import sigecop.backend.security.model.Rol;
import sigecop.backend.security.repository.PaginaRepository;
import sigecop.backend.security.repository.PermisoRepository;
import sigecop.backend.security.repository.RolRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService extends ServiceGeneric<PermisoResponse, PermisoRequest, Permiso> {

    private final PermisoRepository permisoRepository;
    @Autowired
    PaginaRepository paginaRepository;
    @Autowired
    RolRepository rolRepository;

    public PermisoService(PermisoRepository _permisoRepository) {
        super(PermisoResponse.class,_permisoRepository);
        this.permisoRepository = _permisoRepository;
    }

    @Override
    public List<Permiso> listBase(PermisoRequest filter) {
        return permisoRepository.findByFilter(filter.getRolId(),filter.getPaginaId());
    }

    @Override
    public ObjectResponse<Permiso> recordToEntityEdit(Permiso entity, PermisoRequest request) {
        Pagina pagina;
        Rol rol;
        Optional<Pagina> paginaOptional = paginaRepository.findById(request.getPaginaId());
        if (paginaOptional.isPresent()) {
            pagina=paginaOptional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró la página ingresada",
                    null);
        }
        Optional<Rol> rolOptional = rolRepository.findById(request.getRolId());
        if (rolOptional.isPresent()) {
            rol=rolOptional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró el rol ingresado",
                    null);
        }
        entity.setPagina(pagina);
        entity.setRol(rol);
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Permiso> recordToEntityNew(PermisoRequest request) {
        Pagina pagina;
        Rol rol;
        Optional<Pagina> paginaOptional = paginaRepository.findById(request.getPaginaId());
        if (paginaOptional.isPresent()) {
            pagina=paginaOptional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró la página ingresada",
                    null);
        }
        Optional<Rol> rolOptional = rolRepository.findById(request.getRolId());
        if (rolOptional.isPresent()) {
            rol=rolOptional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró el rol ingresado",
                    null);
        }
        return new ObjectResponse<>(Boolean.TRUE,null,new Permiso(
                request.getId(),
                rol,
                pagina
        ));
    }

}
