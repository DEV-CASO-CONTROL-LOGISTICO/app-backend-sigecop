package sigecop.backend.gestion.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.ObligacionRequest;
import sigecop.backend.gestion.dto.ObligacionResponse;
import sigecop.backend.gestion.service.ObligacionService;
import sigecop.backend.utils.generic.ControllerBase;

/**
 *
 * @author Diego
 */

@RestController
@RequestMapping(path = "api/v1/obligacion")
@Validated
public class ObligacionController extends ControllerBase<ObligacionResponse, ObligacionRequest> {
    private final ObligacionService obligacionService;

    public ObligacionController(ObligacionService _obligacionService) {
        super(_obligacionService);
        this.obligacionService = _obligacionService;
    }
    
    
}
