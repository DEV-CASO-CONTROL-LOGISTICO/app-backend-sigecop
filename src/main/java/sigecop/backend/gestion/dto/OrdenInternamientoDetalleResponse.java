package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.OrdenInternamientoDetalle;
import sigecop.backend.gestion.model.PedidoProducto;
import sigecop.backend.master.dto.ProductoResponse;
import sigecop.backend.utils.generic.DtoGeneric;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdenInternamientoDetalleResponse extends DtoGeneric<OrdenInternamientoDetalle, OrdenInternamientoDetalleResponse> {

    private Integer id;
    private Integer ordenInternamientoId;
    private ProductoResponse producto;
    private Integer cantidad;

    @Override
    protected OrdenInternamientoDetalleResponse mapEntityToDto(OrdenInternamientoDetalle entity, OrdenInternamientoDetalleResponse dto) {
        dto.setId(entity.getId());
        dto.setOrdenInternamientoId(entity.getOrdenInternamiento().getId());
        dto.setProducto(ProductoResponse.fromEntity(entity.getProducto(),ProductoResponse.class));
        dto.setCantidad(entity.getCantidad());
        return dto;
    }

}