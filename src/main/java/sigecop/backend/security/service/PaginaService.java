package sigecop.backend.security.service;

import org.springframework.stereotype.Service;
import sigecop.backend.security.dto.PaginaRequest;
import sigecop.backend.security.dto.PaginaResponse;
import sigecop.backend.security.dto.RolRequest;
import sigecop.backend.security.dto.RolResponse;
import sigecop.backend.security.model.Pagina;
import sigecop.backend.security.model.Rol;
import sigecop.backend.security.repository.PaginaRepository;
import sigecop.backend.security.repository.RolRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

import java.util.List;

@Service
public class PaginaService extends ServiceGeneric<PaginaResponse, PaginaRequest, Pagina> {

    private final PaginaRepository paginaRepository;

    public PaginaService(PaginaRepository _paginaRepository) {
        super(PaginaResponse.class,_paginaRepository);
        this.paginaRepository = _paginaRepository;
    }

    @Override
    public List<Pagina> listBase(PaginaRequest filter) {
        return paginaRepository.findByFilter(filter.getNombre());
    }

    @Override
    public ObjectResponse<Pagina> recordToEntityEdit(Pagina entity, PaginaRequest request) {
        entity.setNombre(request.getNombre());
        entity.setUrl(request.getUrl());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Pagina> recordToEntityNew(PaginaRequest request) {
        return new ObjectResponse<>(Boolean.TRUE,null,new Pagina(
                request.getId(),
                request.getNombre(),
                request.getUrl()
        ));
    }


}
