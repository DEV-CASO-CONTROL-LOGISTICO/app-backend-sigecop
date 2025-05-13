package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.SolicitudProveedor;
import sigecop.backend.master.dto.ProveedorResponse;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Diego Poma
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudProveedorResponse extends DtoGeneric<SolicitudProveedor,SolicitudProveedorResponse>{
    private Integer id;
    private ProveedorResponse proveedor;
    private SolicitudResponse solicitud;

     @Override
    protected SolicitudProveedorResponse mapEntityToDto(SolicitudProveedor entity, SolicitudProveedorResponse dto) {
        dto.setId(entity.getId());
        dto.setProveedor(ProveedorResponse.fromEntity(entity.getProveedor(), ProveedorResponse.class));
        dto.setSolicitud(SolicitudResponse.fromEntity(entity.getSolicitud(), SolicitudResponse.class));
        return dto;
    }

}
