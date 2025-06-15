
package sigecop.backend.gestion.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sigecop.backend.gestion.dto.EstadoPedidoRequest;
import sigecop.backend.gestion.dto.EstadoPedidoResponse;
import sigecop.backend.gestion.service.EstadoPedidoService;
import sigecop.backend.utils.generic.ControllerBase;

/**
 *
 * @author Diego Poma
 */
@RestController
@RequestMapping(path = "api/v1/estado_pedido")
@Validated
public class EstadoPedidoController extends ControllerBase<EstadoPedidoResponse, EstadoPedidoRequest>{
    private final EstadoPedidoService estadoPedidoService;
    
    public EstadoPedidoController(EstadoPedidoService _estadoPedidoService) {
        super(_estadoPedidoService);
        this.estadoPedidoService = _estadoPedidoService;
    }
}
