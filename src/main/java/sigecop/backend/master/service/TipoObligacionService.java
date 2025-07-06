package sigecop.backend.master.service;

import java.util.List;
import org.springframework.stereotype.Service;
import sigecop.backend.master.dto.TipoObligacionRequest;
import sigecop.backend.master.dto.TipoObligacionResponse;
import sigecop.backend.master.model.TipoObligacion;
import sigecop.backend.master.repository.TipoObligacionRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

/**
 *
 * @author Diego
 */

@Service
public class TipoObligacionService extends ServiceGeneric<TipoObligacionResponse, TipoObligacionRequest, TipoObligacion>{
    
     private final TipoObligacionRepository tipoObligacionRepository;
    
    public TipoObligacionService(TipoObligacionRepository _tipoObligacionRepository) {
        super(TipoObligacionResponse.class, _tipoObligacionRepository);
        this.tipoObligacionRepository = _tipoObligacionRepository;
    }

    @Override
    public List<TipoObligacion> listBase(TipoObligacionRequest filter) {
        return tipoObligacionRepository.findByFilter();
    }

    @Override
    public ObjectResponse<TipoObligacion> recordToEntityEdit(TipoObligacion entity, TipoObligacionRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<TipoObligacion> recordToEntityNew(TipoObligacionRequest request) {
        return new ObjectResponse<>(Boolean.TRUE,null,new TipoObligacion(
                request.getId(),
                request.getNombre(),
                request.getDescripcion(),
                Boolean.FALSE
        ));
    }
    
}
