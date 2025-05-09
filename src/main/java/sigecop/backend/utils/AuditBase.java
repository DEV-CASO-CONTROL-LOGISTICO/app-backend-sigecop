package sigecop.backend.utils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AuditBase {

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

}
