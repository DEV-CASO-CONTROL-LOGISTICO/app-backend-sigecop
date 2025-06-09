
package sigecop.backend.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.EstadoPedidoRequest;
import sigecop.backend.gestion.dto.EstadoPedidoResponse;
import sigecop.backend.gestion.model.EstadoPedido;
import sigecop.backend.gestion.repository.EstadoPedidoRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

/**
 *
 * @author Diego Poma
 */

@Service
public class EstadoPedidoService extends ServiceGeneric<EstadoPedidoResponse, EstadoPedidoRequest, EstadoPedido>{

    private final EstadoPedidoRepository estadoPedidoRepository;
    
    public EstadoPedidoService(EstadoPedidoRepository _estadoPedidoRepository) {
        super(EstadoPedidoResponse.class, _estadoPedidoRepository);
        this.estadoPedidoRepository = _estadoPedidoRepository;
    }

    @Override
    public List<EstadoPedido> listBase(EstadoPedidoRequest filter) {
        return estadoPedidoRepository.findByFilter();
    }

    @Override
    public ObjectResponse<EstadoPedido> recordToEntityEdit(EstadoPedido entity, EstadoPedidoRequest request) {
        entity.setDescripcion(request.getDescripcion());
        entity.setDetalle(request.getDetalle());
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<EstadoPedido> recordToEntityNew(EstadoPedidoRequest request) {
        return new ObjectResponse<>(Boolean.TRUE, null, new EstadoPedido(
                null,
                request.getDescripcion(),
                request.getDetalle(),
                Boolean.FALSE
        ));
    }

    
}
