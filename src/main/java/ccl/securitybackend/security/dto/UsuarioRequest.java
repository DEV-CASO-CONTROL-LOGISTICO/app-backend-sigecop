/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.dto;

import ccl.securitybackend.utils.generic.RequestBase;
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
public class UsuarioRequest extends RequestBase {

    private Integer rolId;
    private Integer proveedorId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    private String cuenta;
    private String clave;
    private Boolean updatePassword;

    public boolean isValidLogin() {
        return cuenta != null && clave != null && !cuenta.isEmpty() && !clave.isEmpty();
    }
}
