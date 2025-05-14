/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 *
 * @author jhochuq
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionProductoRequest extends RequestBase{

    private Integer cotizacionId;
    private Integer productoId;
    private Integer cantidadSolicitada;
    private Integer cantidadCotizada;
    private BigDecimal precioUnitario;
}
