/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sigecop.backend.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.gestion.model.EstadoCotizacion;
import sigecop.backend.utils.generic.DtoGeneric;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 *
 * @author jhochuq
 */
public class EstadoCotizacionResponse extends DtoGeneric<EstadoCotizacion,EstadoCotizacionResponse>{

    private Integer id;
    private String descripcion;
    private String detalle;
    
    @Override
    protected EstadoCotizacionResponse mapEntityToDto(EstadoCotizacion entity, EstadoCotizacionResponse dto) {
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setDetalle(entity.getDetalle());
        return dto;
    }
}
