
package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.Pedido;
import sigecop.backend.gestion.model.PedidoProducto;
import sigecop.backend.master.dto.ProveedorResponse;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.utils.Constantes;
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
    private ProveedorResponse proveedor;
    private EstadoPedidoResponse estado;
    private String descripcion;
    private String observacion;
    private BigDecimal montoTotal;
    private UsuarioResponse usuarioCreacion;
    private UsuarioResponse usuarioEstado;    
    private Date fechaRegistro;
    private String numeroFactura;
    private String serieGuia;
    private String numeroGuia;
    private Date fechaEntrega;
    private String observacionEnvio;
    private List<PedidoProducto> pedidoProducto;
    
    @Override
    protected PedidoResponse mapEntityToDto(Pedido entity, PedidoResponse dto) {
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setProveedor(ProveedorResponse.fromEntity(entity.getProveedor(),ProveedorResponse.class));
        dto.setEstado(EstadoPedidoResponse.fromEntity(entity.getEstado(), EstadoPedidoResponse.class));
        dto.setDescripcion(entity.getDescripcion());
        dto.setObservacion(entity.getObservacion());
        dto.setMontoTotal(entity.getMontoTotal());
        dto.setUsuarioCreacion(UsuarioResponse.fromEntity(entity.getUsuarioCreacion(), UsuarioResponse.class));
        dto.setUsuarioEstado(UsuarioResponse.fromEntity(entity.getUsuarioEstado(), UsuarioResponse.class));
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setNumeroFactura(entity.getNumeroFactura());
        dto.setSerieGuia(entity.getSerieGuia());
        dto.setNumeroGuia(entity.getNumeroGuia());
        dto.setFechaEntrega(entity.getFechaEntrega());
        dto.setObservacionEnvio(entity.getObservacionEnvio());        
        return dto;
    }
    
    public List<PedidoProducto> getPedidoProducto() {
        if (pedidoProducto == null) {
            pedidoProducto = new ArrayList<>();
        }
        return pedidoProducto;
    }

    public Boolean getEsGenerado() {
        return estado != null && estado.getId() != null && estado.getId().equals(Constantes.EstadoPedido.GENERADO);
    }
    public Boolean getEsEnviado() {
        return estado != null && estado.getId() != null && estado.getId().equals(Constantes.EstadoPedido.ENVIADO);
    }
    public Boolean getEsConforme() {
        return estado != null && estado.getId() != null && estado.getId().equals(Constantes.EstadoPedido.CON_CONFORMIDAD);
    }
    public Boolean getEsDevuelto() {
        return estado != null && estado.getId() != null && estado.getId().equals(Constantes.EstadoPedido.DEVUELTO);
    }
    public Boolean getEsPagado() {
        return estado != null && estado.getId() != null && estado.getId().equals(Constantes.EstadoPedido.PAGADO);
    }
}
