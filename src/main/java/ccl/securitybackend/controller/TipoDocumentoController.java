package ccl.securitybackend.controller;

import ccl.securitybackend.dto.RolResponse;
import ccl.securitybackend.dto.TipoDocumentoResponse;
import ccl.securitybackend.service.TipoDocumentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tipoDocumento")
@Validated
public class TipoDocumentoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TipoDocumentoService tipoDocumentoService;

    @PostMapping("/list")
    public ResponseEntity<?> list() {
        List<TipoDocumentoResponse> listaTipoDocumento = null;
        try {
            listaTipoDocumento = tipoDocumentoService.list();

        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaTipoDocumento.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        return ResponseEntity.ok(listaTipoDocumento);
    }

}
