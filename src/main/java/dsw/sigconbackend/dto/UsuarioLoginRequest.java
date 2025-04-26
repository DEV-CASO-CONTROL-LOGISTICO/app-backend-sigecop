/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsw.sigconbackend.dto;

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
public class UsuarioLoginRequest {

    private String cuenta;
    private String clave;

    public boolean isValid() {
        return cuenta != null && clave != null && !cuenta.isEmpty() && !clave.isEmpty();
    }
}
