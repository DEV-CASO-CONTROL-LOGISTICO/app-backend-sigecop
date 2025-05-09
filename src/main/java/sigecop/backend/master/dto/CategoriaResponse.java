package sigecop.backend.master.dto;

import sigecop.backend.master.model.Categoria;
import sigecop.backend.utils.generic.DtoGeneric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponse extends DtoGeneric<Categoria,CategoriaResponse> {
    private Integer id;
    private String nombre;

    @Override
    protected CategoriaResponse mapEntityToDto(Categoria entity, CategoriaResponse dto) {
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
