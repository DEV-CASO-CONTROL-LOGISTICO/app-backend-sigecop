
package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

/**
 *
 * @author Diego Poma
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoRequest extends RequestBase{

    private Integer pedidoId;
    private Integer productoId;
    private Integer cantidad;
}
