/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.gestion.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 *
 * @author jhochuq
 */
public class CotizacionRequest extends RequestBase {
    
    private Integer solicitudId;
    private Integer solicitudProveedorId;
    private String codigo;
    private Integer estadoId;
    private BigDecimal monto;
    private Date fechaEmision;
    private String comentario;
    private Integer usuarioCreacionId;
    private List<CotizacionProductoRequest> cotizacionProducto;

    public List<CotizacionProductoRequest> getCotizacionProducto() {
        if (cotizacionProducto == null) {
            cotizacionProducto = new ArrayList<>();
        }
        return cotizacionProducto;
    }

}
