
package sigecop.backend.gestion.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.PedidoRequest;
import sigecop.backend.gestion.dto.PedidoResponse;
import sigecop.backend.gestion.model.Pedido;
import sigecop.backend.gestion.model.PedidoProducto;
import sigecop.backend.gestion.repository.PedidoProductoRepository;
import sigecop.backend.gestion.repository.PedidoRepository;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.master.repository.ProveedorRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

/**
 *
 * @author Diego Poma
 */
@Service
public class PedidoService extends ServiceGeneric<PedidoResponse, PedidoRequest, Pedido>{
    
    private final PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;
    
    public PedidoService(PedidoRepository _pedidoRepository) {
        super(PedidoResponse.class, _pedidoRepository);
        this.pedidoRepository = _pedidoRepository;
    }

    @Override
    public List<Pedido> listBase(PedidoRequest filter) {
        return pedidoRepository.findByFilter(
                filter.getProveedorId(),
                filter.getCodigo(),
                filter.getDescripcion(),
                filter.getEstadoId()
        );
    }

    @Override
    public ObjectResponse<PedidoResponse> postFind(PedidoResponse response) {
        if (response != null && response.getId() != null) {                        
            List<PedidoProducto> productos = pedidoProductoRepository.findByFilter(response.getId());
            productos = productos != null ? productos : new ArrayList<>();
            response.setPedidoProducto(productos);
        }
        return new ObjectResponse<>(Boolean.TRUE, null, response);
    }
    
    @Override
    public ObjectResponse<Pedido> recordToEntityEdit(Pedido entity, PedidoRequest request) {        
        entity.setObservacionEnvio(request.getObservacionEnvio());     
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
    
    @Override
    public ObjectResponse<Pedido> recordToEntityNew(PedidoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();
        
        Proveedor proveedor;
        Optional<Proveedor> optionalProveedor = proveedorRepository.findById(userId);
        if (optionalProveedor.isPresent()) {
            proveedor = optionalProveedor.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el usuario de sesión",
                    null
            );
        }
        
        Usuario usuario;
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(userId);
        if (optionalUsuario.isPresent()) {
            usuario = optionalUsuario.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el usuario de sesión",
                    null
            );
        }
        
        Pedido entity = Pedido.builder()
                .codigo(request.getCodigo())
                .proveedor(proveedor)
                .descripcion(request.getDescripcion())
                .observacion(request.getObservacion())
                .montoTotal(request.getMontoTotal())
                .usuarioCreacion(usuario)
                .usuarioEstado(usuario)
                .fechaRegistro(request.getFechaRegistro())
                .numeroFactura(request.getNumeroFactura())
                .serieGuia(request.getSerieGuia())
                .numeroGuia(request.getNumeroGuia())
                .fechaEntrega(request.getFechaEntrega())
                .build();
        
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
    
    
    
}
