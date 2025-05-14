/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.gestion.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.CotizacionRequest;
import sigecop.backend.gestion.dto.CotizacionResponse;
import sigecop.backend.gestion.service.CotizacionService;
import sigecop.backend.utils.generic.ControllerBase;

/**
 *
 * @author Moises_F16.7.24
 */
@RestController
@RequestMapping(path = "api/v1/cotizacion")
@Validated
public class CotizacionController extends ControllerBase<CotizacionResponse, CotizacionRequest> {

    private final CotizacionService cotizacionService;

    public CotizacionController(CotizacionService _cotizacionService) {
        super(_cotizacionService);
        this.cotizacionService = _cotizacionService;
    }
}
