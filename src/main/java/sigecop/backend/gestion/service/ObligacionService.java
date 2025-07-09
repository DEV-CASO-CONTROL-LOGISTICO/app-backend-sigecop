package sigecop.backend.gestion.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.ObligacionRequest;
import sigecop.backend.gestion.dto.ObligacionResponse;
import sigecop.backend.gestion.model.EstadoObligacion;
import sigecop.backend.gestion.model.Obligacion;
import sigecop.backend.gestion.model.Pedido;
import sigecop.backend.gestion.repository.EstadoObligacionRepository;
import sigecop.backend.gestion.repository.ObligacionRepository;
import sigecop.backend.gestion.repository.PedidoRepository;
import sigecop.backend.master.model.TipoObligacion;
import sigecop.backend.master.repository.TipoObligacionRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.Constantes;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

/**
 *
 * @author Diego
 */

@Service
public class ObligacionService extends ServiceGeneric<ObligacionResponse, ObligacionRequest, Obligacion> {

    private final ObligacionRepository obligacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoObligacionRepository tipoObligacionRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EstadoObligacionRepository estadoObligacionRepository;
    
    public ObligacionService(ObligacionRepository _obligacionRepository) {
        super(ObligacionResponse.class, _obligacionRepository);
        this.obligacionRepository = _obligacionRepository;
    }

    @Override
    public List<Obligacion> listBase(ObligacionRequest filter) {
        return obligacionRepository.findByFilter(
                filter.getEstadoId(),
                filter.getPedidoId(),
                filter.getCodigo(),
                filter.getTipoId(),
                filter.getDescripcion(),
                filter.getProveedorRazonSocial()
        );
    }

    public Obligacion findByIdWithProductos(Integer id) {
        return obligacionRepository.findByIdWithPedidoAndProductos(id)
            .orElseThrow(() -> new EntityNotFoundException("Obligación no encontrada"));
    }
        
    @Override
    public ObjectResponse<Obligacion> recordToEntityEdit(Obligacion entity, ObligacionRequest request) {
        TipoObligacion tipoObligacion;
        Optional<TipoObligacion> optionalTipo = tipoObligacionRepository.findById(request.getTipoId());
        if (optionalTipo.isPresent()) {
            tipoObligacion = optionalTipo.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el tipo de Obligacion",
                    null
            );
        }
        entity.setDescripcion(request.getDescripcion());
        entity.setTipo(tipoObligacion);
        entity.setFechaPago(request.getFechaPago());
        entity.setNombreUsuarioPago(request.getNombreUsuarioPago());
        entity.setCuentaBancariaTemporal(request.getCuentaBancariaTemporal());
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<Obligacion> recordToEntityNew(ObligacionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(userId);
        if (optionalUsuario.isEmpty()) {
            return new ObjectResponse<>(false, "No se encontró el usuario de sesión", null);
        }       
        Usuario usuario = optionalUsuario.get();
        
        Optional<TipoObligacion> optionalTipo = tipoObligacionRepository.findById(request.getTipoId());
        if (optionalTipo.isEmpty()) {
            return new ObjectResponse<>(false, "No se encontró el tipo de obligación", null);
        }
        TipoObligacion tipoObligacion = optionalTipo.get();

        Optional<EstadoObligacion> optionalEstado = estadoObligacionRepository.findById(Constantes.EstadoObligacion.PENDIENTE_DE_CONTABILIZAR);
        if (optionalEstado.isEmpty()) {
            return new ObjectResponse<>(false, "No se encontró el estado", null);
        }
        EstadoObligacion estado = optionalEstado.get();

        Pedido pedido = null;
        if (request.getPedidoId() != null) {
            Optional<Pedido> optionalPedido = pedidoRepository.findById(request.getPedidoId());
            if (optionalPedido.isEmpty()) {
                return new ObjectResponse<>(false, "No se encontró el pedido", null);
            }
            pedido = optionalPedido.get();
        }

        Obligacion entity = Obligacion.builder()
                .pedido(pedido)
                .tipo(tipoObligacion)
                .estado(estado)
                .descripcion(request.getDescripcion())
                .monto(request.getMonto())
                .usuarioCreacion(usuario)
                .usuarioEstado(usuario)
                .fechaRegistro(new Date())
                .fechaPago(null)
                .nombreUsuarioPago(request.getNombreUsuarioPago())
                .cuentaBancariaTemporal(request.getCuentaBancariaTemporal())
                .build();

        return new ObjectResponse<>(true, null, entity);
    }

    
    public ObjectResponse registrarPago(ObligacionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        Optional<Obligacion> optional = obligacionRepository.findById(request.getId());
        if (optional.isEmpty()) {
            return new ObjectResponse<>(false, "No se encontró la obligación", null);
        }

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(userId);
        if (optionalUsuario.isEmpty()) {
            return new ObjectResponse<>(false, "No se encontró el usuario actual", null);
        }

        Optional<EstadoObligacion> optionalEstado = estadoObligacionRepository.findById(Constantes.EstadoObligacion.ENVIADO_POR_APROBACION);
        if (optionalEstado.isEmpty()) {
            return new ObjectResponse<>(false, "No se encontró el estado 'Pago Registrado'", null);
        }

        Obligacion obligacion = optional.get();
        obligacion.setEstado(optionalEstado.get());
        obligacion.setMonto(request.getMonto());
        obligacion.setDescripcion(request.getDescripcion());
        obligacion.setUsuarioEstado(optionalUsuario.get());
        obligacion.setFechaRegistro(new Date());
        obligacionRepository.save(obligacion);

        return new ObjectResponse<>(true, "Pago registrado correctamente", null);
    }

    public ObjectResponse<Obligacion> changeEstado(ObligacionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

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
        
        Obligacion obligacion;
        Optional<Obligacion> optionalObligacion = obligacionRepository.findById(request.getId());
        
        if (optionalObligacion.isPresent()) {
            obligacion = optionalObligacion.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró la obligacion",
                    null
            );
        }
        
        EstadoObligacion estadoObligacion;
        Optional<EstadoObligacion> optionalEstado = estadoObligacionRepository.findById(request.getEstadoId());
        if (optionalEstado.isPresent()) {
            estadoObligacion = optionalEstado.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado",
                    null
            );
        }
        
        //ESTADO CAMBIA DE OBLIGACION ;
        obligacion.setEstado(estadoObligacion);
        obligacion.setComentario(request.getComentario());
        obligacionRepository.save(obligacion);

        return new ObjectResponse<>(Boolean.TRUE, null, obligacion);
    }
    
}
