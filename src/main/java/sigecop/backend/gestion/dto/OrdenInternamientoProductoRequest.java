package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenInternamientoProductoRequest extends RequestBase {

    private Integer ordenId;
    private Integer productoId;
    private Integer cantidad;
}
