
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
import sigecop.backend.utils.Constantes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.bind.annotation.RequestParam;
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
                    ? ResponseEntity.ok(resultOperation.getObject())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/devolver")
    public ResponseEntity<?> devolver(@RequestBody PedidoRequest request) {
        try {
            ObjectResponse resultOperation = pedidoService.devolver(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/pedidoProveedor")
    public ResponseEntity<?> listPedidoByProveedor(@RequestBody PedidoRequest request) {
        try { 
            return ResponseEntity.ok(pedidoService.listPedidoByProveedor(request));
        } catch (Exception e) {
            //loggerBase.error("Error list "+this.getClass().getName(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }
    
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarPedido(@RequestBody PedidoRequest request) {
        try {
            ObjectResponse resultOperation = pedidoService.enviarPedido(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/uploadFactura")
    public ResponseEntity<?> uploadFactura(@RequestParam("pedidoId") String idPedido,
                                      @RequestParam("file") MultipartFile file,@RequestParam("numero") String numero) {
        try {
            ObjectResponse resultOperation = pedidoService.guardarArchivo(file,idPedido,Constantes.TipoArchivo.FACTURA,numero);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/uploadGuia")
    public ResponseEntity<?> uploadGuia(@RequestParam("pedidoId") String idPedido,
                                      @RequestParam("file") MultipartFile file,@RequestParam("numero") String numero) {
        try {
            ObjectResponse resultOperation = pedidoService.guardarArchivo(file,idPedido,Constantes.TipoArchivo.GUIA,numero);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/downloadGuia")
    public ResponseEntity<?> downloadGuia(@RequestBody PedidoRequest request) {
        try {
            ObjectResponse resultOperation = pedidoService.downloadGuia(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/downloadFactura")
    public ResponseEntity<?> downloadFactura(@RequestBody PedidoRequest request) {
        try {
            ObjectResponse resultOperation = pedidoService.downloadFactura(request);
            return resultOperation.getSuccess()
                    ? ResponseEntity.ok(resultOperation.getSuccess())
                    : ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resultOperation.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
