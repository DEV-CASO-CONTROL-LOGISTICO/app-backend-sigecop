
package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.Pedido;
import sigecop.backend.gestion.model.PedidoProducto;
import sigecop.backend.master.dto.ProveedorResponse;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.security.model.Usuario;
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
public class PedidoResponse extends DtoGeneric<Pedido, PedidoResponse>{
    
    private Integer id;
    private String codigo;
    private String descripcion;
    private String observacion; 
    private UsuarioResponse usuarioRegistro;    
    private Date fechaRegistro;
    private ProveedorResponse proveedor;
    private BigDecimal monto_total;
    private EstadoPedidoResponse estado;
    private List<PedidoProducto> pedidoProducto;
    
    @Override
    protected PedidoResponse mapEntityToDto(Pedido entity, PedidoResponse dto) {
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setUsuarioRegistro(UsuarioResponse.fromEntity(entity.getUsuarioRegistro(), UsuarioResponse.class));
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setProveedor(ProveedorResponse.fromEntity(entity.getProveedor(),ProveedorResponse.class));
        dto.setMonto_total(entity.getMonto_total());
        dto.setEstado(EstadoPedidoResponse.fromEntity(entity.getEstado(), EstadoPedidoResponse.class));
        return dto;
    }
    
    public List<PedidoProducto> getPedidoProducto() {
        if (pedidoProducto == null) {
            pedidoProducto = new ArrayList<>();
        }
        return pedidoProducto;
    }
   
}
