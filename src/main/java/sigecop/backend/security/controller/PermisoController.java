package sigecop.backend.security.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.security.dto.PaginaRequest;
import sigecop.backend.security.dto.PaginaResponse;
import sigecop.backend.security.dto.PermisoRequest;
import sigecop.backend.security.dto.PermisoResponse;
import sigecop.backend.security.service.PaginaService;
import sigecop.backend.security.service.PermisoService;
import sigecop.backend.utils.generic.ControllerBase;

@RestController
@RequestMapping(path = "api/v1/permiso")
@Validated
public class PermisoController extends ControllerBase<PermisoResponse, PermisoRequest> {
    private final PermisoService permisoService;

    public PermisoController(PermisoService _permisoService) {
        super(_permisoService);
        this.permisoService=_permisoService;
    }

}
