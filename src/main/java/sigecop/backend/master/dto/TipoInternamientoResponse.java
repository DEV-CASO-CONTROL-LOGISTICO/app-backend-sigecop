package sigecop.backend.master.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.TipoInternamiento;
import sigecop.backend.utils.generic.DtoGeneric;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoInternamientoResponse extends DtoGeneric<TipoInternamiento,TipoInternamientoResponse> {

    private String nombre;
    private String descripcion;
    private Boolean valorDefecto;

    @Override
    protected TipoInternamientoResponse mapEntityToDto(TipoInternamiento entity, TipoInternamientoResponse dto) {
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setValorDefecto(entity.getValorDefecto());
        return dto;
    }
}
