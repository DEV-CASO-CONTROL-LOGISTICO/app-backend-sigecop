
package sigecop.backend.gestion.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.SolicitudRequest;
import sigecop.backend.gestion.dto.SolicitudResponse;
import sigecop.backend.gestion.service.SolicitudService;
import sigecop.backend.utils.generic.ControllerBase;



@RestController
@RequestMapping(path = "api/v1/solicitud")
@Validated
public class SolicitudController extends ControllerBase<SolicitudResponse, SolicitudRequest>{
    
   private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService _solicitudService) {
        super(_solicitudService);
        this.solicitudService = _solicitudService;
    }
   
}
