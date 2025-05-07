package ccl.securitybackend.master.service;

import ccl.securitybackend.master.dto.CategoriaRequest;
import ccl.securitybackend.master.dto.CategoriaResponse;
import ccl.securitybackend.master.dto.ProductoRequest;
import ccl.securitybackend.master.dto.ProductoResponse;
import ccl.securitybackend.master.model.Categoria;
import ccl.securitybackend.master.model.Producto;
import ccl.securitybackend.master.model.Proveedor;
import ccl.securitybackend.master.repository.CategoriaRepository;
import ccl.securitybackend.master.repository.ProductoRepository;
import ccl.securitybackend.security.model.Rol;
import ccl.securitybackend.utils.ObjectResponse;
import ccl.securitybackend.utils.generic.ServiceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService extends ServiceGeneric<ProductoResponse, ProductoRequest, Producto> {


    private final ProductoRepository productoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository _productoRepository) {
        super(ProductoResponse.class,_productoRepository);
        this.productoRepository = _productoRepository;
    }

    @Override
    public List<Producto> listBase(ProductoRequest filter) {
        return productoRepository.findByFilter(filter.getCategoriaId(),filter.getNombre());
    }

    @Override
    public ObjectResponse<Producto> recordToEntityEdit(Producto entity, ProductoRequest request) {
        Categoria categoria;
        Optional<Categoria> optional = categoriaRepository.findById(request.getCategoriaId());
        if (optional.isPresent()) {
            categoria=optional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró la categoría ingresada",
                    null);
        }
        entity.setCategoria(categoria);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecioUnitario(request.getPrecioUnitario());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Producto> recordToEntityNew(ProductoRequest request) {
        Categoria categoria;
        Optional<Categoria> optional = categoriaRepository.findById(request.getCategoriaId());
        if (optional.isPresent()) {
            categoria=optional.get();
        }else{
            return new ObjectResponse(
                    Boolean.FALSE,
                    "No se encontró la categoría ingresada",
                    null);
        }
        return new ObjectResponse<>(Boolean.TRUE,null,new Producto(
                request.getId(),
                categoria,
                request.getNombre(),
                request.getDescripcion(),
                request.getPrecioUnitario()
        ));
    }

}