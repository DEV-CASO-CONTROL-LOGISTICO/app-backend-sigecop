package ccl.securitybackend.master.controller;

import ccl.securitybackend.master.dto.CategoriaRequest;
import ccl.securitybackend.master.dto.CategoriaResponse;
import ccl.securitybackend.master.service.CategoriaService;
import ccl.securitybackend.utils.generic.ControllerBase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/categoria")
@Validated
public class CategoriaController extends ControllerBase<CategoriaResponse, CategoriaRequest> {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService _categoriaService) {
        super(_categoriaService);
        this.categoriaService=_categoriaService;
    }
}
