package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.Solicitud;
import sigecop.backend.gestion.model.SolicitudProducto;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.utils.Constantes;
import sigecop.backend.utils.generic.DtoGeneric;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolicitudResponse extends DtoGeneric<Solicitud, SolicitudResponse> {

    private Integer id;
    private String codigo;
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaFinalizado;
    private UsuarioResponse usuarioCreacion;
    private UsuarioResponse usuarioEstado;
    private EstadoSolicitudResponse estado;
    private List<Proveedor> proveedores;
    private List<SolicitudProducto> solicitudProducto;

    @Override
    protected SolicitudResponse mapEntityToDto(Solicitud entity, SolicitudResponse dto) {
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaFinalizado(entity.getFechaFinalizado());
        dto.setUsuarioCreacion(UsuarioResponse.fromEntity(entity.getUsuarioCreacion(), UsuarioResponse.class));
        dto.setUsuarioEstado(UsuarioResponse.fromEntity(entity.getUsuarioEstado(), UsuarioResponse.class));
        dto.setEstado(EstadoSolicitudResponse.fromEntity(entity.getEstado(), EstadoSolicitudResponse.class));
        return dto;
    }

    public List<Proveedor> getProveedores() {
        if (proveedores == null) {
            proveedores = new ArrayList<>();
        }
        return proveedores;
    }

    public List<SolicitudProducto> getSolicitudProducto() {
        if (solicitudProducto == null) {
            solicitudProducto = new ArrayList<>();
        }
        return solicitudProducto;
    }

    public Boolean getFinalizado() {
        return estado != null && estado.getId() != null && estado.getId().equals(Constantes.EstadoSolicitud.FINALIZADO);
    }
}
