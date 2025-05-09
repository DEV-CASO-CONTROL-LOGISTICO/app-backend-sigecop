package sigecop.backend.master.controller;

import sigecop.backend.master.dto.ProveedorRequest;
import sigecop.backend.master.dto.ProveedorResponse;
import sigecop.backend.master.service.ProveedorService;
import sigecop.backend.utils.generic.ControllerBase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/proveedor")
@Validated
public class ProveedorController extends ControllerBase<ProveedorResponse, ProveedorRequest> {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService _proveedorService) {
        super(_proveedorService);
        this.proveedorService=_proveedorService;
    }
}
