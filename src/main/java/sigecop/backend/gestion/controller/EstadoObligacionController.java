package sigecop.backend.gestion.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.EstadoObligacionRequest;
import sigecop.backend.gestion.dto.EstadoObligacionResponse;
import sigecop.backend.gestion.service.EstadoObligacionService;
import sigecop.backend.utils.generic.ControllerBase;

/**
 *
 * @author Diego
 */

@RestController
@RequestMapping(path = "api/v1/estado_obligacion")
@Validated
public class EstadoObligacionController extends ControllerBase<EstadoObligacionResponse, EstadoObligacionRequest>{
    
    private final EstadoObligacionService estadoObligacionService;
    
    public EstadoObligacionController(EstadoObligacionService _estadoObligacionService) {
        super(_estadoObligacionService);
        this.estadoObligacionService = _estadoObligacionService;
    }
}
