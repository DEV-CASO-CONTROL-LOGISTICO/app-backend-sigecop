package sigecop.backend.master.service;

import sigecop.backend.master.dto.CategoriaRequest;
import sigecop.backend.master.dto.CategoriaResponse;
import sigecop.backend.master.model.Categoria;
import sigecop.backend.master.model.Producto;
import sigecop.backend.master.repository.CategoriaRepository;
import sigecop.backend.master.repository.ProductoRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService extends ServiceGeneric<CategoriaResponse, CategoriaRequest, Categoria> {


    private final CategoriaRepository categoriaRepository;
    @Autowired
    ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository _categoriaRepository) {
        super(CategoriaResponse.class,_categoriaRepository);
        this.categoriaRepository = _categoriaRepository;
    }

    @Override
    public List<Categoria> listBase(CategoriaRequest filter) {
        return categoriaRepository.findByFilter(filter.getNombre());
    }

    @Override
    public ObjectResponse<Categoria> recordToEntityEdit(Categoria entity, CategoriaRequest request) {
        entity.setNombre(request.getNombre());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Categoria> recordToEntityNew(CategoriaRequest request) {
        return new ObjectResponse<>(Boolean.TRUE,null,new Categoria(
                request.getId(),
                request.getNombre()
        ));
    }

    @Override
    public ObjectResponse validateDelete(CategoriaRequest request){
        List<Producto> result=productoRepository.findByFilter(request.getId(),null);
        return (result==null || result.isEmpty())?
                new ObjectResponse(Boolean.TRUE,null,null):
                new ObjectResponse(Boolean.FALSE,"No puede eliminar mientras exista productos pertenecientes a esta categoría",null);
    }

}