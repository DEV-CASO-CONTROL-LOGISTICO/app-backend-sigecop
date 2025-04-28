/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.dto;

import ccl.securitybackend.model.Rol;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class RolResponse {

    private Integer id;
    private String nombre;

    public static RolResponse fromEntity(Rol rol) {
        if (rol == null) {
            return new RolResponse();
        }
        return RolResponse.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .build();
    }

    public static List<RolResponse> fromEntities(List<Rol> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        return users.stream()
                .map(RolResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
