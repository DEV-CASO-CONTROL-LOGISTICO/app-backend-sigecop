package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.EstadoSolicitud;
import sigecop.backend.utils.generic.DtoGeneric;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoSolicitudResponse extends DtoGeneric<EstadoSolicitud,EstadoSolicitudResponse>{
    private Integer id;
    private String descripcion;
    private String detalle;
    
   @Override
   protected EstadoSolicitudResponse mapEntityToDto(EstadoSolicitud entity, EstadoSolicitudResponse dto) {
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDetalle(entity.getDetalle());
        return dto;
    }
}
