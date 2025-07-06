package sigecop.backend.gestion.service;

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
                filter.getPedidoId(),
                filter.getCodigo(),
                filter.getTipoId(),
                filter.getDescripcion()
        );
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
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<Obligacion> recordToEntityNew(ObligacionRequest request) {
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

        TipoObligacion tipoObligacion;
        Optional<TipoObligacion> optionalTipo = tipoObligacionRepository.findById(request.getTipoId());
        if (optionalTipo.isPresent()) {
            tipoObligacion = optionalTipo.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el tipo de obligacion",
                    null
            );
        }

        Pedido pedido=null;
        if(request.getPedidoId()!=null && request.getPedidoId()>0){
            Optional<Pedido> optionalPedido = pedidoRepository.findById(request.getPedidoId());
            if (optionalPedido.isPresent()) {
                pedido = optionalPedido.get();
            } else {
                return new ObjectResponse<>(
                        Boolean.FALSE,
                        "No se encontró el pedido",
                        null
                );
            }
        }
        
        EstadoObligacion estado;
        Optional<EstadoObligacion> optionalEstado = estadoObligacionRepository.findById(Constantes.EstadoObligacion.PAGO_REGISTRADO);
        if (optionalEstado.isPresent()) {
            estado = optionalEstado.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado",
                    null
            );
        }

        Obligacion entity = Obligacion.builder()
                .pedido(pedido)
                .tipo(tipoObligacion)
                .estado(estado)
                .descripcion(request.getDescripcion())
                .monto(request.getMonto())
                .fechaRegistro(new Date())
                .usuarioCreacion(usuario)
                .usuarioEstado(usuario)
                .build();

        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
    
}
