package sigecop.backend.gestion.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

/**
 *
 * @author Diego Poma
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudProductoRequest extends RequestBase{
   
    private Integer solicitudId;
    private Integer productoId;
    private Integer cantidad;
}
