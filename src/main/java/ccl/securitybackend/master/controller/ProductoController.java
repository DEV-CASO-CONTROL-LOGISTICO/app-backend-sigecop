package ccl.securitybackend.master.controller;

import ccl.securitybackend.master.dto.CategoriaRequest;
import ccl.securitybackend.master.dto.CategoriaResponse;
import ccl.securitybackend.master.dto.ProductoRequest;
import ccl.securitybackend.master.dto.ProductoResponse;
import ccl.securitybackend.master.service.CategoriaService;
import ccl.securitybackend.master.service.ProductoService;
import ccl.securitybackend.utils.generic.ControllerBase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/producto")
@Validated
public class ProductoController extends ControllerBase<ProductoResponse, ProductoRequest> {

    private final ProductoService productoService;

    public ProductoController(ProductoService _productoService) {
        super(_productoService);
        this.productoService=_productoService;
    }
}
