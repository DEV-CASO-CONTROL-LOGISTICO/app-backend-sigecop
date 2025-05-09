package sigecop.backend.master.dto;

import sigecop.backend.utils.generic.RequestBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest extends RequestBase {

    private Integer categoriaId;
    private String nombre;
    private String descripcion;
    private BigDecimal precioUnitario;
}
