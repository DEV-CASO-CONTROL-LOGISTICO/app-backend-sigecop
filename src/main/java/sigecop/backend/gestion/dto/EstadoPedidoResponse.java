
package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.EstadoPedido;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Diego Poma
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstadoPedidoResponse extends DtoGeneric<EstadoPedido, EstadoPedidoResponse>{
    private Integer id;
    private String descripcion;
    private String detalle;
    
    @Override
    protected EstadoPedidoResponse mapEntityToDto(EstadoPedido entity, EstadoPedidoResponse dto) {
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDetalle(entity.getDetalle());
        return dto;
    }
}
