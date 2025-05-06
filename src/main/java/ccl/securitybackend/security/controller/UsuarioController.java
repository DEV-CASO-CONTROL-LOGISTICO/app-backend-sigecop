/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.controller;

import ccl.securitybackend.security.dto.UsuarioRequest;
import ccl.securitybackend.security.dto.UsuarioResponse;
import ccl.securitybackend.security.service.UsuarioService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Moises_F16.7.24
 */
@RestController
@RequestMapping(path = "api/v1/usuario")
@Validated
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody UsuarioRequest usuarioRequest) {
        List<UsuarioResponse> lista = null;
        try {
            lista = usuarioService.list(usuarioRequest);
        } catch (Exception e) {
            logger.error("Error getPersonas", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List User not found");
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse;
        try {
            usuarioResponse = usuarioService.find(usuarioRequest.getId());
        } catch (Exception e) {
            logger.error("Error find", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (usuarioResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario not found");
        }
        return ResponseEntity.ok(usuarioResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse;
        try {
            usuarioResponse = usuarioService.save(usuarioRequest);
        } catch (Exception e) {
            logger.error("Error save", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (usuarioResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User information not save");
        }
        return ResponseEntity.ok(usuarioResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse;
        try {
            usuarioResponse = usuarioService.find(usuarioRequest.getId());
            if (usuarioResponse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for delete");
            }
            usuarioService.delete(usuarioRequest.getId());
        } catch (Exception e) {
            logger.error("Error delete", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(usuarioResponse);
    }

}
