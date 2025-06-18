package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenInternamientoRequest  extends RequestBase {

    private String codigo;
    private Integer tipoId;
    private Integer pedidoId;
    private String descripcion;
    private Integer usuarioCreacionId;
    private Date fechaRegistro;
    private List<OrdenInternamientoDetalleRequest> detalles;

    public List<OrdenInternamientoDetalleRequest> getDetalles() {
        if (detalles == null) {
            detalles = new ArrayList<>();
        }
        return detalles;
    }

}
