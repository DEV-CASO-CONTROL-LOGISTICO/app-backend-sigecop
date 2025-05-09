/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.controller;

import sigecop.backend.security.dto.UsuarioRequest;
import sigecop.backend.security.dto.UsuarioResponse;
import sigecop.backend.security.service.UsuarioService;

import sigecop.backend.utils.generic.ControllerBase;
import org.springframework.validation.annotation.Validated;
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
