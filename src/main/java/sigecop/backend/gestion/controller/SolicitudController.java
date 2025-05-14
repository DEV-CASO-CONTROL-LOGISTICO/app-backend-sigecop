package sigecop.backend.gestion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.SolicitudRequest;
import sigecop.backend.gestion.dto.SolicitudResponse;
import sigecop.backend.gestion.service.SolicitudService;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ControllerBase;

@RestController
@RequestMapping(path = "api/v1/solicitud")
@Validated
public class SolicitudController extends ControllerBase<SolicitudResponse, SolicitudRequest> {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService _solicitudService) {
        super(_solicitudService);
        this.solicitudService = _solicitudService;
    }

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizar(@RequestBody SolicitudRequest request) {
        try {
            ObjectResponse resultOperation = solicitudService.finalizar(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
