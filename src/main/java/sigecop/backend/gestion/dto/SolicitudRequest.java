
package sigecop.backend.gestion.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudRequest extends RequestBase {
    
    private String codigo;
    private String descripcion;    
    private Date fechaCreacion;
    private Date fechaVencimiento;
    private Integer usuarioCreacionId;
    private Integer usuarioEstadoId;
    private Integer estadoId;
    
}
