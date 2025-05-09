package sigecop.backend.master.service;

import sigecop.backend.master.dto.ProductoRequest;
import sigecop.backend.master.dto.ProductoResponse;
import sigecop.backend.master.model.Categoria;
import sigecop.backend.master.model.Producto;
import sigecop.backend.master.repository.CategoriaRepository;
import sigecop.backend.master.repository.ProductoRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
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