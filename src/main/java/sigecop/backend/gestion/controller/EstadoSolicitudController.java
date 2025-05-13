package sigecop.backend.gestion.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.EstadoSolicitudRequest;
import sigecop.backend.gestion.dto.EstadoSolicitudResponse;
import sigecop.backend.gestion.service.EstadoSolicitudService;
import sigecop.backend.utils.generic.ControllerBase;

@RestController
@RequestMapping(path = "api/v1/estado_solicitud")
@Validated
public class EstadoSolicitudController extends ControllerBase<EstadoSolicitudResponse, EstadoSolicitudRequest>{
    
    private final EstadoSolicitudService estadoSolicitudService;
    
    public EstadoSolicitudController(EstadoSolicitudService _estadoSolicitudService) {
        super(_estadoSolicitudService);
        this.estadoSolicitudService = _estadoSolicitudService;
    }
}
