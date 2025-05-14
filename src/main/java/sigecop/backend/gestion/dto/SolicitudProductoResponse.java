package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.SolicitudProducto;
import sigecop.backend.master.dto.ProductoResponse;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Diego Poma
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolicitudProductoResponse extends DtoGeneric<SolicitudProducto,SolicitudProductoResponse> {

    private Integer id;
    private Integer cantidad;
    private SolicitudResponse solicitud;
    private ProductoResponse producto;
    
    @Override
    protected SolicitudProductoResponse mapEntityToDto(SolicitudProducto entity, SolicitudProductoResponse dto) {
        dto.setId(entity.getId());
        dto.setCantidad(entity.getCantidad());
        dto.setSolicitud(SolicitudResponse.fromEntity(entity.getSolicitud(),SolicitudResponse.class));
        dto.setProducto(ProductoResponse.fromEntity(entity.getProducto(),ProductoResponse.class));
        return dto;
    }
}
