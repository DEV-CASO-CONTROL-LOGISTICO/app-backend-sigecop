package sigecop.backend.gestion.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

/**
 *
 * @author Diego
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObligacionRequest extends RequestBase{
    private Integer id;
    private String codigo;
    private Integer pedidoId;
    private Integer tipoId;
    private Integer estadoId;
    private String descripcion;
    private BigDecimal monto;
    private Integer usuarioCreacionId;
    private Integer usuarioEstadoId;
    private Date fechaRegistro; 
    private String proveedorRazonSocial;
}
