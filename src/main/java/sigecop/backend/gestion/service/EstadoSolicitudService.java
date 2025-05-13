
package sigecop.backend.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.EstadoSolicitudRequest;
import sigecop.backend.gestion.dto.EstadoSolicitudResponse;
import sigecop.backend.gestion.model.EstadoSolicitud;
import sigecop.backend.gestion.repository.EstadoSolicitudRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;


@Service
public class EstadoSolicitudService extends ServiceGeneric<EstadoSolicitudResponse, EstadoSolicitudRequest, EstadoSolicitud> {

    private final EstadoSolicitudRepository estadoSolicitudRepository;
    
    public EstadoSolicitudService(EstadoSolicitudRepository _estadoSolicitudRepository) {
        super(EstadoSolicitudResponse.class, _estadoSolicitudRepository);
        this.estadoSolicitudRepository = _estadoSolicitudRepository;
    }

    @Override
    public List<EstadoSolicitud> listBase(EstadoSolicitudRequest filter) {
        return estadoSolicitudRepository.findByFilter();
    }

    @Override
    public ObjectResponse<EstadoSolicitud> recordToEntityEdit(EstadoSolicitud entity, EstadoSolicitudRequest request) {
        entity.setDescripcion(request.getDescripcion());
        entity.setDetalle(request.getDetalle());
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<EstadoSolicitud> recordToEntityNew(EstadoSolicitudRequest request) {
        return new ObjectResponse<>(Boolean.TRUE, null, new EstadoSolicitud(
                null,
                request.getDescripcion(),
                request.getDetalle(),
                Boolean.FALSE
        ));
    }

}


