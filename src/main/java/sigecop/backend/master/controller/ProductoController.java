package sigecop.backend.master.controller;

import sigecop.backend.master.dto.ProductoRequest;
import sigecop.backend.master.dto.ProductoResponse;
import sigecop.backend.master.service.ProductoService;
import sigecop.backend.utils.generic.ControllerBase;
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
