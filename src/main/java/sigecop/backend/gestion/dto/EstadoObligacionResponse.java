package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.EstadoObligacion;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Diego
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoObligacionResponse extends DtoGeneric<EstadoObligacion, EstadoObligacionResponse>{
    private Integer id;
    private String descripcion;
    private String detalle;
    
    @Override
    protected EstadoObligacionResponse mapEntityToDto(EstadoObligacion entity, EstadoObligacionResponse dto) {
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDetalle(entity.getDetalle());
        return dto;
    }
}
