package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author Diego Poma
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudProveedorRequest extends RequestBase{
    
    private Integer solicitudId;
    private Integer proveedorId;
}
