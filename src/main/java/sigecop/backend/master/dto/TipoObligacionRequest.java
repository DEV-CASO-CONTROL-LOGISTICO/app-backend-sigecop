package sigecop.backend.master.dto;

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
public class TipoObligacionRequest extends RequestBase{
    private String nombre;
    private String descripcion;
    private Boolean valorDefecto;
}
