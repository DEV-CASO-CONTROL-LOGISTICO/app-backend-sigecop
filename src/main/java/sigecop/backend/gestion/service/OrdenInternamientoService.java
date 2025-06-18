package sigecop.backend.gestion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.*;
import sigecop.backend.gestion.model.*;
import sigecop.backend.gestion.repository.*;
import sigecop.backend.master.model.Producto;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.master.model.TipoInternamiento;
import sigecop.backend.master.repository.ProductoRepository;
import sigecop.backend.master.repository.ProveedorRepository;
import sigecop.backend.master.repository.TipoInternamientoRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenInternamientoService extends ServiceGeneric<OrdenInternamientoResponse, OrdenInternamientoRequest, OrdenInternamiento> {

    private final OrdenInternamientoRepository ordenInternamientoRepository;
    @Autowired
    private OrdenInternamientoDetalleRepository ordenInternamientoDetalleRepository;
    @Autowired
    private TipoInternamientoRepository tipoInternamientoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public OrdenInternamientoService(OrdenInternamientoRepository _ordenInternamientoRepository) {
        super(OrdenInternamientoResponse.class, _ordenInternamientoRepository);
        this.ordenInternamientoRepository = _ordenInternamientoRepository;
    }

    @Override
    public List<OrdenInternamiento> listBase(OrdenInternamientoRequest filter) {
        return ordenInternamientoRepository.findByFilter(
                filter.getCodigo(),
                filter.getTipoId(),
                filter.getDescripcion()
        );
    }

    @Override
    public ObjectResponse<OrdenInternamientoResponse> postFind(OrdenInternamientoResponse response) {
        if (response != null && response.getId() != null) {
            List<OrdenInternamientoDetalle> detalles = ordenInternamientoDetalleRepository.findByFilter(response.getId());
            detalles = detalles != null ? detalles : new ArrayList<>();
            response.setDetalles(OrdenInternamientoDetalleResponse.fromEntities(detalles,OrdenInternamientoDetalleResponse.class));
        }
        return new ObjectResponse<>(Boolean.TRUE, null, response);
    }

    @Override
    public ObjectResponse<OrdenInternamiento> recordToEntityEdit(OrdenInternamiento entity, OrdenInternamientoRequest request) {
        TipoInternamiento tipoInternamiento;
        Optional<TipoInternamiento> optionalTipo = tipoInternamientoRepository.findById(request.getTipoId());
        if (optionalTipo.isPresent()) {
            tipoInternamiento = optionalTipo.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el tipo de internamiento",
                    null
            );
        }
        entity.setDescripcion(request.getDescripcion());
        entity.setTipo(tipoInternamiento);
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<OrdenInternamiento> recordToEntityNew(OrdenInternamientoRequest request) {
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

        TipoInternamiento tipoInternamiento;
        Optional<TipoInternamiento> optionalTipo = tipoInternamientoRepository.findById(request.getTipoId());
        if (optionalTipo.isPresent()) {
            tipoInternamiento = optionalTipo.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el tipo de internamiento",
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

        OrdenInternamiento entity = OrdenInternamiento.builder()
                .tipo(tipoInternamiento)
                .pedido(pedido)
                .descripcion(request.getDescripcion())
                .fechaRegistro(new Date())
                .usuarioCreacion(usuario)
                .build();

        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse postSave(OrdenInternamientoRequest request, OrdenInternamiento entity) {
        List<OrdenInternamientoDetalle> detalleInicial = ordenInternamientoDetalleRepository.findByFilter(entity.getId());
        detalleInicial = detalleInicial != null ? detalleInicial : new ArrayList<>();
        for (OrdenInternamientoDetalle di : detalleInicial) {
            di.setActivo(Boolean.FALSE);
            ordenInternamientoDetalleRepository.save(di);
        }
        for (OrdenInternamientoDetalleRequest detalleRequest : request.getDetalles()) {
            Optional<Producto> optionalProducto = productoRepository.findById(detalleRequest.getProductoId());
            if (optionalProducto.isEmpty()) {
                return new ObjectResponse(Boolean.TRUE, "Uno de los productos enviados no existe", null);
            }

            Optional<OrdenInternamientoDetalle> optionalResult = detalleInicial.stream()
                    .filter(p -> p.getId().equals(detalleRequest.getId()))
                    .findFirst();
            if (optionalResult.isPresent()) {
                OrdenInternamientoDetalle detalleEdit = optionalResult.get();
                detalleEdit.setActivo(Boolean.TRUE);
                detalleEdit.setCantidad(detalleRequest.getCantidad());
                detalleEdit.setProducto(optionalProducto.get());
                ordenInternamientoDetalleRepository.save(detalleEdit);
            } else {
                OrdenInternamientoDetalle detalleNew = new OrdenInternamientoDetalle(null, entity, optionalProducto.get(), detalleRequest.getCantidad());
                ordenInternamientoDetalleRepository.save(detalleNew);
            }
        }

        return new ObjectResponse(Boolean.TRUE, null, null);
    }


}
