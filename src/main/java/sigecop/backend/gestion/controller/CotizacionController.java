/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.gestion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.CotizacionRequest;
import sigecop.backend.gestion.dto.CotizacionResponse;
import sigecop.backend.gestion.dto.SolicitudProveedorRequest;
import sigecop.backend.gestion.service.CotizacionService;
import sigecop.backend.utils.ObjectResponse;
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

    @PostMapping("/aprobar")
    public ResponseEntity<?> aprobar(@RequestBody CotizacionRequest request) {
        try {
            ObjectResponse resultOperation = cotizacionService.aprobar(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/archivar")
    public ResponseEntity<?> archivar(@RequestBody CotizacionRequest request) {
        try {
            ObjectResponse resultOperation = cotizacionService.archivar(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
