/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.dto;

import ccl.securitybackend.master.dto.ProveedorResponse;
import ccl.securitybackend.security.model.Usuario;
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
    private ProveedorResponse proveedor;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private List<PaginaResponse> paginas;

    public static UsuarioResponse fromEntity(Usuario user) {
        if (user == null) {
            return new UsuarioResponse();
        }
        return UsuarioResponse.builder()
                .id(user.getId())
                .rol(RolResponse.fromEntity(user.getRol()))
                .proveedor(ProveedorResponse.fromEntity(user.getProveedor()))
                .nombre(user.getNombre())
                .apellidoPaterno(user.getApellidoPaterno())
                .apellidoMaterno(user.getApellidoMaterno())
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

    public ProveedorResponse getProveedor() {
        if (proveedor == null) {
            proveedor = new ProveedorResponse();
        }
        return proveedor;
    }

    public List<PaginaResponse> getPaginas() {
        if (paginas == null) {
            paginas = new ArrayList<>();
        }
        return paginas;
    }

}
