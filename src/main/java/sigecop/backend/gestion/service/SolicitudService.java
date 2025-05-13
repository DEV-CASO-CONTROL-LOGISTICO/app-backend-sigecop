
package sigecop.backend.gestion.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.SolicitudRequest;
import sigecop.backend.gestion.dto.SolicitudResponse;
import sigecop.backend.gestion.model.EstadoSolicitud;
import sigecop.backend.gestion.model.Solicitud;
import sigecop.backend.gestion.repository.SolicitudProductoRepository;
import sigecop.backend.gestion.repository.SolicitudProveedorRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
import sigecop.backend.gestion.repository.SolicitudRepository;


@Service
public class SolicitudService extends ServiceGeneric<SolicitudResponse, SolicitudRequest, Solicitud> {

    private final SolicitudRepository solicitudRepository;
    @Autowired
    private SolicitudProveedorRepository solicitudProveedorRepository;
    @Autowired
    private SolicitudProductoRepository solicitudProductoRepository;


    public SolicitudService(SolicitudRepository _solicitudRepository) {
        super(SolicitudResponse.class, _solicitudRepository);
        this.solicitudRepository = _solicitudRepository;
    }

    @Override
    public List<Solicitud> listBase(SolicitudRequest filter) {
        return solicitudRepository.findByFilter(
                filter.getCodigo(),
                filter.getDescripcion(),
                filter.getEstadoId()
        );
    }


    @Override
    public ObjectResponse<Solicitud> recordToEntityEdit(Solicitud entity, SolicitudRequest request) {
        EstadoSolicitud estadoSolicitud;
        Optional<EstadoSolicitud> optionalEstado = estadoSolicitudRepository.findById(request.getEstadoSolicitud());
        if (optionalEstado.isPresent()) {
            estadoSolicitud = optionalEstado.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado ingresado",
                    null
            );
        }

        Usuario usuarioModificacion;
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(request.getUsuarioModificacion());
        if (optionalUsuario.isPresent()) {
            usuarioModificacion = optionalUsuario.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el usuario ingresado",
                    null
            );
        }

        entity.setDescripcion(request.getDescripcion());
        entity.setFechaCreacion(request.getFechaCreacion());
        entity.setFechaModificacion(request.getFechaModificacion());
        entity.setUsuarioModificacion(usuarioModificacion);
        entity.setEstadoSolicitud(estadoSolicitud);

        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<Solicitud> recordToEntityNew(SolicitudRequest request) {
        EstadoSolicitud estadoSolicitud;
        Optional<EstadoSolicitud> optionalEstado = estadoSolicitudRepository.findById(request.getEstadoSolicitud());
        if (optionalEstado.isPresent()) {
            estadoSolicitud = optionalEstado.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado ingresado",
                    null
            );
        }

        Usuario usuarioModificacion;
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(request.getUsuarioModificacion());
        if (optionalUsuario.isPresent()) {
            usuarioModificacion = optionalUsuario.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el usuario ingresado",
                    null
            );
        }

        Solicitud entity = Solicitud.builder()
                .descripcion(request.getDescripcion())
                .fechaCreacion(request.getFechaCreacion())
                .fechaModificacion(request.getFechaModificacion())
                .usuarioModificacion(usuarioModificacion)
                .estadoSolicitud(estadoSolicitud)
                .build();

        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
}


