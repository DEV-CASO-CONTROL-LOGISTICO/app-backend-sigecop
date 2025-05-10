/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.security.model.Permiso;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Moises_F16.7.24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoResponse extends DtoGeneric<Permiso, PermisoResponse> {

    private Integer id;
    private RolResponse rol;
    private PaginaResponse pagina;

    @Override
    protected PermisoResponse mapEntityToDto(Permiso entity, PermisoResponse dto) {
        dto.setId(entity.getId());
        dto.setRol(RolResponse.fromEntity(entity.getRol(), RolResponse.class));
        dto.setPagina(PaginaResponse.fromEntity(entity.getPagina(), PaginaResponse.class));
        return dto;
    }

}
