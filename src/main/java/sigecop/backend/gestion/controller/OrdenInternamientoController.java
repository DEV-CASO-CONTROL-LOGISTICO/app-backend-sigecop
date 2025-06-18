package sigecop.backend.gestion.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.OrdenInternamientoRequest;
import sigecop.backend.gestion.dto.OrdenInternamientoResponse;
import sigecop.backend.gestion.service.OrdenInternamientoService;
import sigecop.backend.utils.generic.ControllerBase;


@RestController
@RequestMapping(path = "api/v1/ordenInternamiento")
@Validated
public class OrdenInternamientoController extends ControllerBase<OrdenInternamientoResponse, OrdenInternamientoRequest> {

    private final OrdenInternamientoService ordenInternamientoService;

    public OrdenInternamientoController(OrdenInternamientoService _ordenInternamientoService) {
        super(_ordenInternamientoService);
        this.ordenInternamientoService = _ordenInternamientoService;
    }
}
