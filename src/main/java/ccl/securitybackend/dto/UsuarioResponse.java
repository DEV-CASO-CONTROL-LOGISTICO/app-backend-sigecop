/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.dto;

import ccl.securitybackend.model.Usuario;
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
public class UsuarioResponse {

    private Integer id;
    private RolResponse rol;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String empresa;
    private String correo;
    private List<PaginaResponse> paginas;

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

    public static List<UsuarioResponse> fromEntities(List<Usuario> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        return users.stream()
                .map(UsuarioResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public RolResponse getRol() {
        if (rol == null) {
            rol = new RolResponse();
        }
        return rol;
    }

    public List<PaginaResponse> getPaginas() {
        if (paginas == null) {
            paginas = new ArrayList<>();
        }
        return paginas;
    }

}
