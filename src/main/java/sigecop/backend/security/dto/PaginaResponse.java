/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.dto;

import sigecop.backend.security.model.Pagina;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.DtoGeneric;

/**
 *
 * @author Moises_F16.7.24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginaResponse extends DtoGeneric<Pagina, PaginaResponse> {

    private Integer id;
    private String nombre;
    private String url;

    @Override
    protected PaginaResponse mapEntityToDto(Pagina entity, PaginaResponse dto) {
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setUrl(entity.getUrl());
        return dto;
    }

}
