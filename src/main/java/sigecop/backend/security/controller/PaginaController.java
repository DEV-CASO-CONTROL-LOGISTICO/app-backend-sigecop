package sigecop.backend.security.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.security.dto.PaginaRequest;
import sigecop.backend.security.dto.PaginaResponse;
import sigecop.backend.security.dto.UsuarioRequest;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.security.service.PaginaService;
import sigecop.backend.security.service.UsuarioService;
import sigecop.backend.utils.generic.ControllerBase;

@RestController
@RequestMapping(path = "api/v1/pagina")
@Validated
public class PaginaController extends ControllerBase<PaginaResponse, PaginaRequest> {
    private final PaginaService paginaService;

    public PaginaController(PaginaService _paginaService) {
        super(_paginaService);
        this.paginaService=_paginaService;
    }

}
