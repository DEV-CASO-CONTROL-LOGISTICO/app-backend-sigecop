package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.Obligacion;
import sigecop.backend.master.dto.TipoObligacionResponse;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Diego
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObligacionResponse extends DtoGeneric<Obligacion, ObligacionResponse>{
    private Integer id;
    private String codigo;
    private PedidoResponse pedido;
    private TipoObligacionResponse tipo;
    private EstadoObligacionResponse estado;
    private String descripcion;
    private BigDecimal monto;
    private UsuarioResponse usuarioCreacion;
    private UsuarioResponse usuarioEstado;
    private Date fechaRegistro;  

    @Override
    protected ObligacionResponse mapEntityToDto(Obligacion entity, ObligacionResponse dto) {
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setPedido(PedidoResponse.fromEntity(entity.getPedido(),PedidoResponse.class));
        dto.setTipo(TipoObligacionResponse.fromEntity(entity.getTipo(), TipoObligacionResponse.class));
        dto.setEstado(EstadoObligacionResponse.fromEntity(entity.getEstado(), EstadoObligacionResponse.class));
        dto.setDescripcion(entity.getDescripcion());
        dto.setMonto(entity.getMonto());
        dto.setUsuarioCreacion(UsuarioResponse.fromEntity(entity.getUsuarioCreacion(), UsuarioResponse.class));
        dto.setUsuarioEstado(UsuarioResponse.fromEntity(entity.getUsuarioEstado(), UsuarioResponse.class));
        dto.setFechaRegistro(entity.getFechaRegistro());        
        return dto;
    }
    
    public Boolean getAutogenerado() {
        return pedido != null && pedido.getId() != null;
    }
}
