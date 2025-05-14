package sigecop.backend.gestion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.SolicitudProductoRequest;
import sigecop.backend.gestion.dto.SolicitudRequest;
import sigecop.backend.gestion.dto.SolicitudResponse;
import sigecop.backend.gestion.model.Solicitud;
import sigecop.backend.gestion.model.SolicitudProducto;
import sigecop.backend.gestion.model.SolicitudProveedor;
import sigecop.backend.gestion.repository.SolicitudProductoRepository;
import sigecop.backend.gestion.repository.SolicitudProveedorRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
import sigecop.backend.gestion.repository.SolicitudRepository;
import sigecop.backend.master.model.Producto;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.master.repository.ProductoRepository;
import sigecop.backend.master.repository.ProveedorRepository;
import sigecop.backend.security.repository.UsuarioRepository;

@Service
public class SolicitudService extends ServiceGeneric<SolicitudResponse, SolicitudRequest, Solicitud> {

    private final SolicitudRepository solicitudRepository;
    @Autowired
    private SolicitudProveedorRepository solicitudProveedorRepository;
    @Autowired
    private SolicitudProductoRepository solicitudProductoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public SolicitudService(SolicitudRepository _solicitudRepository) {
        super(SolicitudResponse.class, _solicitudRepository);
        this.solicitudRepository = _solicitudRepository;
    }

    @Override
    public List<Solicitud> listBase(SolicitudRequest filter) {
        return solicitudRepository.findByFilter(
                filter.getCodigo(),
                filter.getDescripcion(),
                filter.getEstadoId()
        );
    }

    @Override
    public ObjectResponse<SolicitudResponse> postFind(SolicitudResponse response) {
        if (response != null && response.getId() != null) {
            List<SolicitudProveedor> proveedores = solicitudProveedorRepository.findByFilters(response.getId());
            proveedores = proveedores != null ? proveedores : new ArrayList<>();
            response.setProveedores(proveedores.stream()
                    .map(p -> p.getProveedor())
                    .collect(Collectors.toList()));

            List<SolicitudProducto> productos = solicitudProductoRepository.findByFilter(response.getId());
            productos = productos != null ? productos : new ArrayList<>();
            response.setSolicitudProducto(productos);
        }
        return new ObjectResponse<>(Boolean.TRUE, null, response);
    }

    @Override
    public ObjectResponse<Solicitud> recordToEntityEdit(Solicitud entity, SolicitudRequest request) {
        entity.setDescripcion(request.getDescripcion());
        entity.setFechaCreacion(request.getFechaCreacion());
        entity.setFechaVencimiento(request.getFechaVencimiento());

        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse<Solicitud> recordToEntityNew(SolicitudRequest request) {
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

        Solicitud entity = Solicitud.builder()
                .codigo(request.getCodigo())
                .descripcion(request.getDescripcion())
                .fechaCreacion(request.getFechaCreacion())
                .fechaVencimiento(request.getFechaVencimiento())
                .usuarioCreacion(usuario)
                .usuarioEstado(usuario)
                .build();

        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }

    @Override
    public ObjectResponse postSave(SolicitudRequest request, Solicitud entity) {
        List<SolicitudProveedor> solicitudProveedorInicial = solicitudProveedorRepository.findByFilters(entity.getId());
        solicitudProveedorInicial = solicitudProveedorInicial != null ? solicitudProveedorInicial : new ArrayList<>();
        for (SolicitudProveedor sp : solicitudProveedorInicial) {
            sp.setActivo(Boolean.FALSE);
            solicitudProveedorRepository.save(sp);
        }
        for (Integer proveedorId : request.getProveedores()) {
            Optional<Proveedor> optionalProveedor = proveedorRepository.findById(proveedorId);
            if (optionalProveedor.isEmpty()) {
                return new ObjectResponse(Boolean.TRUE, "Uno de los proveedores enviados no existe", null);
            }

            Optional<SolicitudProveedor> optionalResult = solicitudProveedorInicial.stream()
                    .filter(p -> p.getProveedor().getId().equals(proveedorId))
                    .findFirst();
            if (optionalResult.isPresent()) {
                SolicitudProveedor solicitudProveedorEdit = optionalResult.get();
                solicitudProveedorEdit.setActivo(Boolean.TRUE);
                solicitudProveedorRepository.save(solicitudProveedorEdit);
            } else {
                SolicitudProveedor solicitudProveedorNew = new SolicitudProveedor(null, optionalProveedor.get(), entity);
                solicitudProveedorRepository.save(solicitudProveedorNew);
            }
        }

        List<SolicitudProducto> solicitudProductoInicial = solicitudProductoRepository.findByFilter(entity.getId());
        solicitudProductoInicial = solicitudProductoInicial != null ? solicitudProductoInicial : new ArrayList<>();
        for (SolicitudProducto sp : solicitudProductoInicial) {
            sp.setActivo(Boolean.FALSE);
            solicitudProductoRepository.save(sp);
        }
        for (SolicitudProductoRequest solicitudProductoRequest : request.getSolicitudProducto()) {
            Optional<Producto> optionalProducto = productoRepository.findById(solicitudProductoRequest.getProductoId());
            if (optionalProducto.isEmpty()) {
                return new ObjectResponse(Boolean.TRUE, "Uno de los productos enviados no existe", null);
            }

            Optional<SolicitudProducto> optionalResult = solicitudProductoInicial.stream()
                    .filter(p -> p.getId().equals(solicitudProductoRequest.getId()))
                    .findFirst();
            if (optionalResult.isPresent()) {
                SolicitudProducto solicitudProductoEdit = optionalResult.get();
                solicitudProductoEdit.setActivo(Boolean.TRUE);
                solicitudProductoEdit.setCantidad(solicitudProductoRequest.getCantidad());
                solicitudProductoEdit.setProducto(optionalProducto.get());
                solicitudProductoRepository.save(solicitudProductoEdit);
            } else {
                SolicitudProducto solicitudProveedorNew = new SolicitudProducto(null, entity, optionalProducto.get(), solicitudProductoRequest.getCantidad());
                solicitudProductoRepository.save(solicitudProveedorNew);
            }
        }

        return new ObjectResponse(Boolean.TRUE, null, null);
    }
}
