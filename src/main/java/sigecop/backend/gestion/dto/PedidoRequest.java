
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
    private Integer proveedorId;
    private Integer estadoId;
    private String descripcion;
    private String observacion;
    private BigDecimal montoTotal;
    private Integer usuarioCreacionId;
    private Integer usuarioEstadoId; 
    private Date fechaRegistro;
    private String numeroFactura;
    private String serieGuia;
    private String numeroGuia;
    private Date fechaEntrega;
    private String observacionEnvio;       
    private List<PedidoProductoRequest> pedidoProducto;
    
    public List<PedidoProductoRequest> getPedidoProducto() {
        if (pedidoProducto == null) {
            pedidoProducto = new ArrayList<>();
        }
        return pedidoProducto;
    }
}
