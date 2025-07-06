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
    private Boolean inicial_automatico;
    private Boolean inicial_manual;
    
    @Override
    protected EstadoObligacionResponse mapEntityToDto(EstadoObligacion entity, EstadoObligacionResponse dto) {
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDetalle(entity.getDetalle());
        dto.setInicial_automatico(entity.getInicial_automatico());
        dto.setInicial_manual(entity.getInicial_manual());
        return dto;
    }
}
