package sigecop.backend.master.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.TipoObligacion;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Diego
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoObligacionResponse extends DtoGeneric<TipoObligacion, TipoObligacionResponse>{
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean valorDefecto;

    @Override
    protected TipoObligacionResponse mapEntityToDto(TipoObligacion entity, TipoObligacionResponse dto) {
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setValorDefecto(entity.getValorDefecto());
        return dto;
    }
        
}
