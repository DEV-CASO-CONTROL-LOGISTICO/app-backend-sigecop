package ccl.securitybackend.master.controller;

import ccl.securitybackend.master.dto.ProveedorRequest;
import ccl.securitybackend.master.dto.ProveedorResponse;
import ccl.securitybackend.master.service.ProveedorService;
import ccl.securitybackend.utils.generic.ControllerBase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/proveedor")
@Validated
public class ProveedorController extends ControllerBase<ProveedorResponse,ProveedorRequest> {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService _proveedorService) {
        super(_proveedorService);
        this.proveedorService=_proveedorService;
    }
}
