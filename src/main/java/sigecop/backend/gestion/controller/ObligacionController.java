package sigecop.backend.gestion.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.ObligacionRequest;
import sigecop.backend.gestion.dto.ObligacionResponse;
import sigecop.backend.gestion.service.ObligacionService;
import sigecop.backend.utils.generic.ControllerBase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sigecop.backend.utils.ObjectResponse;
import org.springframework.http.HttpStatus;
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
    
    @PostMapping("/changeEstado")
    public ResponseEntity<?> changeEstado(@RequestBody ObligacionRequest request) {
        try {
            ObjectResponse resultOperation = obligacionService.changeEstado(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
