package sigecop.backend.master.service;

import org.springframework.stereotype.Service;
import sigecop.backend.master.dto.TipoInternamientoRequest;
import sigecop.backend.master.dto.TipoInternamientoResponse;
import sigecop.backend.master.model.TipoInternamiento;
import sigecop.backend.master.repository.TipoInternamientoRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

import java.util.List;

@Service
public class TipoInternamientoService extends ServiceGeneric<TipoInternamientoResponse, TipoInternamientoRequest, TipoInternamiento> {

    private final TipoInternamientoRepository tipoInternamientoRepository;

    public TipoInternamientoService(TipoInternamientoRepository _tipoInternamientoRepository) {
        super(TipoInternamientoResponse.class,_tipoInternamientoRepository);
        this.tipoInternamientoRepository = _tipoInternamientoRepository;
    }

    @Override
    public List<TipoInternamiento> listBase(TipoInternamientoRequest filter) {
        return tipoInternamientoRepository.findByFilter();
    }

    @Override
    public ObjectResponse<TipoInternamiento> recordToEntityEdit(TipoInternamiento entity, TipoInternamientoRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<TipoInternamiento> recordToEntityNew(TipoInternamientoRequest request) {
        return new ObjectResponse<>(Boolean.TRUE,null,new TipoInternamiento(
                request.getId(),
                request.getNombre(),
                request.getDescripcion(),
                Boolean.FALSE
        ));
    }

}
