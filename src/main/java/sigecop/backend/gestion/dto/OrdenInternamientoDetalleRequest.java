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
public class OrdenInternamientoDetalleRequest  extends RequestBase {

    private Integer ordenInternamientoId;
    private Integer productoId;
    private Integer cantidad;
}
