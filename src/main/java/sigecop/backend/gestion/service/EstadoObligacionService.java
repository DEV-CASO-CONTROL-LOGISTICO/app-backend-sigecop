package sigecop.backend.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.EstadoObligacionRequest;
import sigecop.backend.gestion.dto.EstadoObligacionResponse;
import sigecop.backend.gestion.model.EstadoObligacion;
import sigecop.backend.gestion.repository.EstadoObligacionRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

/**
 *
 * @author Diego
 */

@Service
public class EstadoObligacionService extends ServiceGeneric<EstadoObligacionResponse, EstadoObligacionRequest, EstadoObligacion>{

    private final EstadoObligacionRepository estadoObligacionRepository;
    
    public EstadoObligacionService(EstadoObligacionRepository _estadoObligacionRepository) {
        super(EstadoObligacionResponse.class, _estadoObligacionRepository);
        this.estadoObligacionRepository = _estadoObligacionRepository;
    }

    @Override
    public List<EstadoObligacion> listBase(EstadoObligacionRequest filter) {
        return estadoObligacionRepository.findByFilter();
    }

    @Override
    public ObjectResponse<EstadoObligacion> recordToEntityEdit(EstadoObligacion entity, EstadoObligacionRequest request) {
        entity.setDescripcion(request.getDescripcion());
        entity.setDetalle(request.getDetalle());
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<EstadoObligacion> recordToEntityNew(EstadoObligacionRequest request) {
        return new ObjectResponse<>(Boolean.TRUE, null, new EstadoObligacion(
                null,
                request.getDescripcion(),
                request.getDetalle(),
                Boolean.FALSE,
                Boolean.FALSE
        ));
    }
}
