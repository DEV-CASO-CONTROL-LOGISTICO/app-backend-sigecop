package sigecop.backend.master.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.master.dto.TipoObligacionRequest;
import sigecop.backend.master.dto.TipoObligacionResponse;
import sigecop.backend.master.service.TipoObligacionService;
import sigecop.backend.utils.generic.ControllerBase;

/**
 *
 * @author Diego
 */

@RestController
@RequestMapping(path = "api/v1/tipoObligacion")
@Validated
public class TipoObligacionController extends ControllerBase<TipoObligacionResponse, TipoObligacionRequest> {

    private final TipoObligacionService tipoObligacionService;

    public TipoObligacionController(TipoObligacionService _tipoObligacionService) {
        super(_tipoObligacionService);
        this.tipoObligacionService=_tipoObligacionService;
    }
}
