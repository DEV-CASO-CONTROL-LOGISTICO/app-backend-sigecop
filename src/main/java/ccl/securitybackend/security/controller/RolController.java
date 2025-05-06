/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccl.securitybackend.security.controller;

import ccl.securitybackend.security.dto.RolRequest;
import ccl.securitybackend.security.dto.RolResponse;
import ccl.securitybackend.security.dto.UsuarioRequest;
import ccl.securitybackend.security.dto.UsuarioResponse;
import ccl.securitybackend.security.service.RolService;
import java.util.List;

import ccl.securitybackend.security.service.UsuarioService;
import ccl.securitybackend.utils.generic.ControllerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Moises_F16.7.24
 */
@RestController
@RequestMapping(path = "api/v1/rol")
@Validated
public class RolController extends ControllerBase<RolResponse, RolRequest> {

    private final RolService rolService;

    public RolController(RolService _rolService) {
        super(_rolService);
        this.rolService=_rolService;
    }
}
