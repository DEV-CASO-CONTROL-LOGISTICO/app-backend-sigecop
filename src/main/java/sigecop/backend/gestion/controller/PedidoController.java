
package sigecop.backend.gestion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.CotizacionRequest;
import sigecop.backend.gestion.dto.PedidoRequest;
import sigecop.backend.gestion.dto.PedidoResponse;
import sigecop.backend.gestion.service.PedidoService;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ControllerBase;

/**
 *
 * @author Diego Poma
 */
@RestController
@RequestMapping(path = "api/v1/pedido")
@Validated
public class PedidoController extends ControllerBase<PedidoResponse, PedidoRequest> {
    
    private final PedidoService pedidoService;

    public PedidoController(PedidoService _pedidoService) {
        super(_pedidoService);
        this.pedidoService = _pedidoService;
    }

    @PostMapping("/darConformidad")
    public ResponseEntity<?> darConformidad(@RequestBody PedidoRequest request) {
        try {
            ObjectResponse resultOperation = pedidoService.darConformidad(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
