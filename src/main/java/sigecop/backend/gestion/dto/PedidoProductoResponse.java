
package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.PedidoProducto;
import sigecop.backend.master.dto.ProductoResponse;
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
public class PedidoProductoResponse extends DtoGeneric<PedidoProducto, PedidoProductoResponse> {

    private Integer id;
    private PedidoResponse pedido;
    private ProductoResponse producto;
    private Integer cantidad;
    
    @Override
    protected PedidoProductoResponse mapEntityToDto(PedidoProducto entity, PedidoProductoResponse dto) {
        dto.setId(entity.getId());
        dto.setPedido(PedidoResponse.fromEntity(entity.getPedido(),PedidoResponse.class));
        dto.setProducto(ProductoResponse.fromEntity(entity.getProducto(),ProductoResponse.class));
        dto.setCantidad(entity.getCantidad());
        return dto;
    }
    
}
