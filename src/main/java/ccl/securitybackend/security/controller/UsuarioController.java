/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.controller;

import ccl.securitybackend.master.dto.ProveedorRequest;
import ccl.securitybackend.master.dto.ProveedorResponse;
import ccl.securitybackend.master.service.ProveedorService;
import ccl.securitybackend.security.dto.UsuarioRequest;
import ccl.securitybackend.security.dto.UsuarioResponse;
import ccl.securitybackend.security.service.UsuarioService;
import java.util.List;

import ccl.securitybackend.utils.generic.ControllerBase;
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
public class UsuarioController extends ControllerBase<UsuarioResponse, UsuarioRequest> {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService _usuarioService) {
        super(_usuarioService);
        this.usuarioService=_usuarioService;
    }

}
