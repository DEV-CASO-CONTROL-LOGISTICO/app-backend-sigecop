package ccl.securitybackend.dto;

import ccl.securitybackend.model.Rol;
import ccl.securitybackend.model.TipoDocumento;
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
public class TipoDocumentoResponse {

    private Integer id;
    private String nombre;

    public static TipoDocumentoResponse fromEntity(TipoDocumento tipoDocumento) {
        if (tipoDocumento == null) {
            return new TipoDocumentoResponse();
        }
        return TipoDocumentoResponse.builder()
                .id(tipoDocumento.getId())
                .nombre(tipoDocumento.getNombre())
                .build();
    }

    public static List<TipoDocumentoResponse> fromEntities(List<TipoDocumento> tipoDocumentos) {
        if (tipoDocumentos == null) {
            return new ArrayList<>();
        }
        return tipoDocumentos.stream()
                .map(TipoDocumentoResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
