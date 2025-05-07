package ccl.securitybackend.master.dto;

import ccl.securitybackend.master.model.Categoria;
import ccl.securitybackend.utils.generic.RequestBase;
import jakarta.persistence.Column;
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
