/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.dto;

import ccl.securitybackend.master.dto.ProveedorResponse;
import ccl.securitybackend.master.model.Proveedor;
import ccl.securitybackend.security.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ccl.securitybackend.utils.generic.DtoGeneric;
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
public class UsuarioResponse extends DtoGeneric<Usuario,UsuarioResponse> {

    private Integer id;
    private RolResponse rol;
    private ProveedorResponse proveedor;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private List<PaginaResponse> paginas;

    @Override
    protected UsuarioResponse mapEntityToDto(Usuario entity, UsuarioResponse dto) {
        dto.setId(entity.getId());
        dto.setRol(RolResponse.fromEntity(entity.getRol(),RolResponse.class));
        dto.setProveedor(ProveedorResponse.fromEntity(entity.getProveedor(),ProveedorResponse.class));
        dto.setNombre(entity.getNombre());
        dto.setApellidoPaterno(entity.getApellidoPaterno());
        dto.setApellidoMaterno(entity.getApellidoMaterno());
        return dto;
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
