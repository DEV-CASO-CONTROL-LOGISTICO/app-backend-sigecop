/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.utils.generic.RequestBase;

/**
 *
 * @author Moises_F16.7.24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginaRequest extends RequestBase {

    private String nombre;
    private String url;
}
