/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.security.controller;

import sigecop.backend.security.dto.RolRequest;
import sigecop.backend.security.dto.RolResponse;
import sigecop.backend.security.service.RolService;

import sigecop.backend.utils.generic.ControllerBase;
import org.springframework.validation.annotation.Validated;
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
