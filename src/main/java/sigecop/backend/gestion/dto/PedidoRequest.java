
package sigecop.backend.gestion.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class PedidoRequest extends RequestBase{
    
    private String codigo;
    private String descripcion;
    private String observacion; 
    private Integer usuarioRegistroId;    
    private Date fechaRegistro;
    private Integer proveedorId;
    private BigDecimal monto_total;
    private Integer estadoId;
    private List<PedidoProductoRequest> pedidoProducto;
    
    public List<PedidoProductoRequest> getPedidoProducto() {
        if (pedidoProducto == null) {
            pedidoProducto = new ArrayList<>();
        }
        return pedidoProducto;
    }
}
