/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.dto;

import sigecop.backend.security.model.Rol;

import sigecop.backend.utils.generic.Constants;
import sigecop.backend.utils.generic.DtoGeneric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Moises_F16.7.24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolResponse extends DtoGeneric<Rol,RolResponse> {

    private Integer id;
    private String codigo;
    private String nombre;

    @Override
    protected RolResponse mapEntityToDto(Rol entity, RolResponse dto) {
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setCodigo(entity.getCodigo());
        return dto;
    }

    public Boolean getIsProveedor(){
        return codigo!=null && codigo.equals(Constants.ROL_PROVEEDOR);
    }

}
