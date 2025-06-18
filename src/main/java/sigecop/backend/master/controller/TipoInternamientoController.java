package sigecop.backend.master.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.master.dto.TipoInternamientoRequest;
import sigecop.backend.master.dto.TipoInternamientoResponse;
import sigecop.backend.master.service.TipoInternamientoService;
import sigecop.backend.utils.generic.ControllerBase;

@RestController
@RequestMapping(path = "api/v1/tipoInternamiento")
@Validated
public class TipoInternamientoController extends ControllerBase<TipoInternamientoResponse, TipoInternamientoRequest> {

    private final TipoInternamientoService tipoInternamientoService;

    public TipoInternamientoController(TipoInternamientoService _tipoInternamientoService) {
        super(_tipoInternamientoService);
        this.tipoInternamientoService=_tipoInternamientoService;
    }
}
