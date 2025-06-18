package sigecop.backend.gestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.OrdenInternamientoProducto;
import sigecop.backend.master.dto.ProductoResponse;
import sigecop.backend.utils.generic.DtoGeneric;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdenInternamientoProductoResponse extends DtoGeneric<OrdenInternamientoProducto, OrdenInternamientoProductoResponse> {

    private Integer id;
    private OrdenInternamientoResponse ordenInternamiento;
    private ProductoResponse producto;
    private Integer cantidad;

    @Override
    protected OrdenInternamientoProductoResponse mapEntityToDto(OrdenInternamientoProducto entity, OrdenInternamientoProductoResponse dto){
        dto.setId(entity.getId());
        dto.setOrdenInternamiento(OrdenInternamientoResponse.fromEntity(entity.getOrdenInternamiento(), OrdenInternamientoResponse.class));
        dto.setProducto(ProductoResponse.fromEntity(entity.getProducto(), ProductoResponse.class));
        dto.setCantidad(entity.getCantidad());
        return dto;
    }
}
