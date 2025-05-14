/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.gestion.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.Cotizacion;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.utils.generic.DtoGeneric;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

/**
 *
 * @author jhochuq
 */
public class CotizacionResponse extends DtoGeneric<Cotizacion, CotizacionResponse> {

    private Integer id;
    private SolicitudProveedorResponse solicitudProveedor;
    private EstadoCotizacionResponse estado;
    private String codigo;
    private BigDecimal monto;
    private Date fechaEmision;
    private String comentario;
    private UsuarioResponse usuarioCreacion;
    private UsuarioResponse usuarioEstado;
    private List<CotizacionProductoResponse> cotizacionProducto;

    @Override
    protected CotizacionResponse mapEntityToDto(Cotizacion entity, CotizacionResponse dto) {
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setMonto(entity.getMonto());
        dto.setFechaEmision(entity.getFechaEmision());
        dto.setComentario(entity.getComentario());
        dto.setSolicitudProveedor(SolicitudProveedorResponse.fromEntity(entity.getSolicitudProveedor(), SolicitudProveedorResponse.class));
        dto.setUsuarioEstado(UsuarioResponse.fromEntity(entity.getUsuarioEstado(), UsuarioResponse.class));
        dto.setUsuarioCreacion(UsuarioResponse.fromEntity(entity.getUsuarioCreacion(), UsuarioResponse.class));
        dto.setEstado(EstadoCotizacionResponse.fromEntity(entity.getEstado(), EstadoCotizacionResponse.class));
        return dto;
    }

    public List<CotizacionProductoResponse> getCotizacionProducto() {
        if (cotizacionProducto == null) {
            cotizacionProducto = new ArrayList<>();
        }
        return cotizacionProducto;
    }
}
