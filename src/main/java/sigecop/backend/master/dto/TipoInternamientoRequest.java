package sigecop.backend.master.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoInternamientoRequest extends RequestBase {

    private String nombre;
    private String descripcion;
    private Boolean valorDefecto;
}
