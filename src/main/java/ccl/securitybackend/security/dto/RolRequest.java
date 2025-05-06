package ccl.securitybackend.security.dto;

import ccl.securitybackend.utils.generic.RequestBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolRequest extends RequestBase {
    private String codigo;
    private String nombre;
}
