package sigecop.backend.master.controller;

import sigecop.backend.master.dto.CategoriaRequest;
import sigecop.backend.master.dto.CategoriaResponse;
import sigecop.backend.master.service.CategoriaService;
import sigecop.backend.utils.generic.ControllerBase;
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
