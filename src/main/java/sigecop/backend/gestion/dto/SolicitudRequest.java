package sigecop.backend.gestion.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private Integer usuarioCreacionId;
    private Integer usuarioEstadoId;
    private Integer estadoId;
    private List<Integer> proveedores;
    private List<SolicitudProductoRequest> solicitudProducto;

    public List<Integer> getProveedores() {
        if (proveedores == null) {
            proveedores = new ArrayList<>();
        }
        return proveedores;
    }

    public List<SolicitudProductoRequest> getSolicitudProducto() {
        if (solicitudProducto == null) {
            solicitudProducto = new ArrayList<>();
        }
        return solicitudProducto;
    }
}
