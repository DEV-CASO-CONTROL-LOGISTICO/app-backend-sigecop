/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsw.sigconbackend.dto;

import dsw.sigconbackend.model.Usuario;
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
public class UsuarioResponse {

    private Integer id;
    private RolResponse rol;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String empresa;
    private String correo;

    public static UsuarioResponse fromEntity(Usuario user) {
        if (user == null) {
            return new UsuarioResponse();
        }
        return UsuarioResponse.builder()
                .id(user.getId())
                .rol(RolResponse.fromEntity(user.getRol()))
                .nombre(user.getNombre())
                .apellidoPaterno(user.getApellidoPaterno())
                .apellidoMaterno(user.getApellidoMaterno())
                .empresa(user.getEmpresa())
                .correo(user.getCorreo())
                .build();
    }

}
