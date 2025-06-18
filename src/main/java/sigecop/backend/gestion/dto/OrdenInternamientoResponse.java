package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.Cotizacion;
import sigecop.backend.gestion.model.OrdenInternamiento;
import sigecop.backend.gestion.model.OrdenInternamientoDetalle;
import sigecop.backend.gestion.model.Pedido;
import sigecop.backend.master.dto.TipoInternamientoResponse;
import sigecop.backend.master.model.TipoInternamiento;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.utils.generic.DtoGeneric;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdenInternamientoResponse extends DtoGeneric<OrdenInternamiento, OrdenInternamientoResponse> {

    private Integer id;
    private String codigo;
    private TipoInternamientoResponse tipo;
    private PedidoResponse pedido;
    private String descripcion;
    private UsuarioResponse usuarioCreacion;
    private Date fechaRegistro;
    private List<OrdenInternamientoDetalleResponse> detalles;

    @Override
    protected OrdenInternamientoResponse mapEntityToDto(OrdenInternamiento entity, OrdenInternamientoResponse dto) {
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setTipo(TipoInternamientoResponse.fromEntity(entity.getTipo(), TipoInternamientoResponse.class));
        dto.setPedido(PedidoResponse.fromEntity(entity.getPedido(), PedidoResponse.class));
        dto.setDescripcion(entity.getDescripcion());
        dto.setUsuarioCreacion(UsuarioResponse.fromEntity(entity.getUsuarioCreacion(), UsuarioResponse.class));
        dto.setFechaRegistro(entity.getFechaRegistro());
        return dto;
    }

    public List<OrdenInternamientoDetalleResponse> getDetalles() {
        if (detalles == null) {
            detalles = new ArrayList<>();
        }
        return detalles;
    }
}
