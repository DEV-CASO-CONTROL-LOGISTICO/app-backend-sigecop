package ccl.securitybackend.master.dto;

import ccl.securitybackend.utils.generic.RequestBase;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequest extends RequestBase {

    private String nombre;
}
