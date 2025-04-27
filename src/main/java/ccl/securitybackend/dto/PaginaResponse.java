/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.dto;

import ccl.securitybackend.model.Pagina;
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
public class PaginaResponse {

    private Integer id;
    private String nombre;
    private String url;

    public static PaginaResponse fromEntity(Pagina pagina) {
        if (pagina == null) {
            return new PaginaResponse();
        }
        return PaginaResponse.builder()
                .id(pagina.getId())
                .nombre(pagina.getNombre())
                .url(pagina.getUrl())
                .build();
    }

    public static List<PaginaResponse> fromEntities(List<Pagina> paginas) {
        if (paginas == null) {
            return new ArrayList<>();
        }
        return paginas.stream()
                .map(PaginaResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
