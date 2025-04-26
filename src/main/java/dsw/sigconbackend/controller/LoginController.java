/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dsw.sigconbackend.controller;

import dsw.sigconbackend.dto.UsuarioLoginRequest;
import dsw.sigconbackend.dto.UsuarioResponse;
import dsw.sigconbackend.service.UsuarioService;
import dsw.sigconbackend.utils.JwtUtil;
import dsw.sigconbackend.utils.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Moises_F16.7.24
 */
@RestController
@RequestMapping(path = "api/v1/session")
@Validated
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginRequest userRequest) {
        UsuarioResponse usuarioResponse = null;
        try {
            if (userRequest == null || !userRequest.isValid()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Ingrese credenciales");
            }
            usuarioResponse = usuarioService.searchForCredentials(userRequest);
            if (usuarioResponse == null || usuarioResponse.getId() == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Credenciales no válidas");
            }
            return ResponseEntity.ok(
                    ObjectResponse.
                            <String>builder()
                            .success(Boolean.TRUE)
                            .object(JwtUtil.generarToken(usuarioResponse))
                            .build()
            );
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/listPagina")
    public ResponseEntity<?> listPagina(@RequestBody UsuarioLoginRequest userRequest) {
        return ResponseEntity.ok("request");
    }
}
