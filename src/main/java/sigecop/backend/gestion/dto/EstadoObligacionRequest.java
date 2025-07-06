package sigecop.backend.gestion.dto;

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
public class EstadoObligacionRequest extends RequestBase{
    private String descripcion;
    private String detalle;
    private Boolean inicial_automatico;
    private Boolean inicial_manual;
}
