/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.dto;

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
public class UsuarioRequest {

    private Integer id;
    private Integer rolId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String empresa;
    private String correo;

    private String cuenta;
    private String clave;
    private Boolean updatePassword;

    public boolean isValidLogin() {
        return cuenta != null && clave != null && !cuenta.isEmpty() && !clave.isEmpty();
    }
}
