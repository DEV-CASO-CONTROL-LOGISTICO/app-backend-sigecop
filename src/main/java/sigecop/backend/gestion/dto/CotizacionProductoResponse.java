/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.CotizacionProducto;
import sigecop.backend.master.dto.ProductoResponse;
import sigecop.backend.utils.generic.DtoGeneric;
import java.math.BigDecimal;

/**
 *
 * @author jhochuq
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionProductoResponse extends DtoGeneric<CotizacionProducto, CotizacionProductoResponse> {

    private Integer id;
    private Integer cantidadSolicitado;
    private Integer cantidadCotizada;
    private BigDecimal precioUnitario;
    private CotizacionResponse cotizacion;
    private ProductoResponse producto;

    @Override
    protected CotizacionProductoResponse mapEntityToDto(CotizacionProducto entity, CotizacionProductoResponse dto) {
        dto.setId(entity.getId());
        dto.setCantidadSolicitado(entity.getCantidadSolicitada());
        dto.setCantidadCotizada(entity.getCantidadCotizada());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setCotizacion(CotizacionResponse.fromEntity(entity.getCotizacion(), CotizacionResponse.class));
        dto.setProducto(ProductoResponse.fromEntity(entity.getProducto(), ProductoResponse.class));
        return dto;
    }
}
