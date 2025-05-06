package ccl.securitybackend.master.dto;

import ccl.securitybackend.master.model.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponse {

    private Integer id;
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String direccion;
    private String telefono;
    private String correo;

    public static ProveedorResponse fromEntity(Proveedor user) {
        if (user == null) {
            return new ProveedorResponse();
        }
        return ProveedorResponse.builder()
                .id(user.getId())
                .ruc(user.getRuc())
                .razonSocial(user.getRazonSocial())
                .nombreComercial(user.getNombreComercial())
                .direccion(user.getDireccion())
                .telefono(user.getTelefono())
                .correo(user.getCorreo())
                .build();
    }

    public static List<ProveedorResponse> fromEntities(List<Proveedor> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        return users.stream()
                .map(ProveedorResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
