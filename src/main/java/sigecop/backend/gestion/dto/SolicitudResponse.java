
package sigecop.backend.gestion.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.Solicitud;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.utils.generic.DtoGeneric;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudResponse extends DtoGeneric<Solicitud,SolicitudResponse>{
    
    private Integer id;
    private String codigo;
    private String descripcion;    
    private Date fechaCreacion;
    private Date fechaVencimiento;
    private UsuarioResponse usuarioCreacion;
    private UsuarioResponse usuarioEstado;
    private EstadoSolicitudResponse estado;

    @Override
    protected SolicitudResponse mapEntityToDto(Solicitud entity, SolicitudResponse dto) {
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaCreacion(entity.getFechaCreacion());       
        dto.setFechaVencimiento(entity.getFechaVencimiento());        
        dto.setUsuarioCreacion(UsuarioResponse.fromEntity(entity.getUsuarioCreacion(),UsuarioResponse.class));
        dto.setUsuarioEstado(UsuarioResponse.fromEntity(entity.getUsuarioEstado(),UsuarioResponse.class));
        dto.setEstado(EstadoSolicitudResponse.fromEntity(entity.getEstado(),EstadoSolicitudResponse.class));
        return dto;
    }
}
