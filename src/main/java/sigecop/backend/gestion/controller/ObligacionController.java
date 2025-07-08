package sigecop.backend.gestion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.ObligacionRequest;
import sigecop.backend.gestion.dto.ObligacionResponse;
import sigecop.backend.gestion.model.Obligacion;
import sigecop.backend.gestion.service.ObligacionService;
import sigecop.backend.utils.ObjectResponse;
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
    
    @Override
    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody ObligacionRequest request) {
        Obligacion obligacion = obligacionService.findByIdWithProductos(request.getId());
        return ResponseEntity.ok(obligacion);
    }

    @PostMapping("/registrar-pago")
    public ResponseEntity<?> registrarPago(@RequestBody ObligacionRequest request) {
        try {
            ObjectResponse result = obligacionService.registrarPago(request);
            return result.getSuccess()
                    ? ResponseEntity.ok(result.getSuccess())
                    : ResponseEntity.unprocessableEntity().body(result.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
