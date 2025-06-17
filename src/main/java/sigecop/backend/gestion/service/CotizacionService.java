/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sigecop.backend.gestion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.CotizacionProductoResponse;
import sigecop.backend.gestion.dto.CotizacionRequest;
import sigecop.backend.gestion.dto.CotizacionProductoRequest;
import sigecop.backend.gestion.dto.CotizacionResponse;
import sigecop.backend.gestion.model.*;
import sigecop.backend.gestion.repository.*;
import sigecop.backend.master.model.Producto;
import sigecop.backend.master.repository.ProductoRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.Constantes;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
import sigecop.backend.utils.Constantes;
/**
 *
 * @author AlexChuky
 */
@Service
public class CotizacionService extends ServiceGeneric<CotizacionResponse, CotizacionRequest, Cotizacion> {
    
    private final CotizacionRepository cotizacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private EstadoCotizacionRepository estadoCotizacionRepository;
    @Autowired
    private EstadoSolicitudRepository estadoSolicitudRepository;
    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CotizacionProductoRepository cotizacionProductoRepository;
    @Autowired
    private SolicitudProveedorRepository solicitudProveedorRepository;
    
    public CotizacionService(CotizacionRepository _cotizacionRepository) {
        super(CotizacionResponse.class, _cotizacionRepository);
        this.cotizacionRepository = _cotizacionRepository;
    }
    
    @Override
    public List<Cotizacion> listBase(CotizacionRequest filter) {
        return cotizacionRepository.findByFilter(
                filter.getSolicitudId(),
                filter.getSolicitudProveedorId(),
                filter.getCodigo(),
                filter.getEstadoId()
        );
    }
    
    @Override
    public ObjectResponse<CotizacionResponse> postFind(CotizacionResponse response) {
        if (response != null && response.getId() != null) {
            List<CotizacionProducto> cotizacionProducto = cotizacionProductoRepository.findByFilter(response.getId());
            cotizacionProducto = cotizacionProducto != null ? cotizacionProducto : new ArrayList<>();
            response.setCotizacionProducto(cotizacionProducto.stream()
                    .map(cp -> CotizacionProductoResponse.fromEntity(cp, CotizacionProductoResponse.class))
                    .collect(Collectors.toList()));
        }
        return new ObjectResponse<>(Boolean.TRUE, null, response);
    }
    
    @Override
    public ObjectResponse<Cotizacion> recordToEntityEdit(Cotizacion entity, CotizacionRequest request) {
        entity.setMonto(request.getMonto());
        entity.setComentario(request.getComentario());
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
    
    @Override
    public ObjectResponse<Cotizacion> recordToEntityNew(CotizacionRequest request) {
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
        SolicitudProveedor solicitudProveedor;
        Optional<SolicitudProveedor> optionalSolicitudProveedor = solicitudProveedorRepository.findById(request.getSolicitudProveedorId());
        if (optionalSolicitudProveedor.isPresent()) {
            solicitudProveedor = optionalSolicitudProveedor.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró la solicitud del proveedor",
                    null
            );
        }
        
        EstadoCotizacion estado;
        Optional<EstadoCotizacion> optionalEstado = estadoCotizacionRepository.findById(Constantes.EstadoCotizacion.GENERADO);
        if (optionalEstado.isPresent()) {
            estado = optionalEstado.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado",
                    null
            );
        }
        
        Cotizacion entity = Cotizacion.builder()
                .solicitudProveedor(solicitudProveedor)
                .estado(estado) //Nace con un estado Generado
                .monto(request.getMonto())
                .comentario(request.getComentario())
                .fechaEmision(new Date())
                .usuarioCreacion(usuario)
                .usuarioEstado(usuario)
                .build();
        
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
    
    @Override
    public ObjectResponse postSave(CotizacionRequest request, Cotizacion entity) {
        List<CotizacionProducto> cotizacionProductoInicial = cotizacionProductoRepository.findByFilter(entity.getId());
        cotizacionProductoInicial = cotizacionProductoInicial != null ? cotizacionProductoInicial : new ArrayList<>();
        for (CotizacionProducto sp : cotizacionProductoInicial) {
            sp.setActivo(Boolean.FALSE);
            cotizacionProductoRepository.save(sp);
        }
        for (CotizacionProductoRequest cotizacionProductoRequest : request.getCotizacionProducto()) {
            Optional<Producto> optionalProducto = productoRepository.findById(cotizacionProductoRequest.getProductoId());
            if (optionalProducto.isEmpty()) {
                return new ObjectResponse(Boolean.TRUE, "Uno de los productos cotizados no existe", null);
            }
            
            Optional<CotizacionProducto> optionalResult = cotizacionProductoInicial.stream()
                    .filter(p -> p.getId().equals(cotizacionProductoRequest.getId()))
                    .findFirst();
            if (optionalResult.isPresent()) {
                CotizacionProducto cotizacionProductoEdit = optionalResult.get();
                cotizacionProductoEdit.setActivo(Boolean.TRUE);
                cotizacionProductoEdit.setCantidadSolicitada(cotizacionProductoRequest.getCantidadSolicitada());
                cotizacionProductoEdit.setCantidadCotizada(cotizacionProductoRequest.getCantidadCotizada());
                cotizacionProductoEdit.setPrecioUnitario(cotizacionProductoRequest.getPrecioUnitario());
                cotizacionProductoEdit.setProducto(optionalProducto.get());
                cotizacionProductoRepository.save(cotizacionProductoEdit);
            } else {
                CotizacionProducto cotizacionProductoNew = new CotizacionProducto(null, entity, optionalProducto.get(), cotizacionProductoRequest.getCantidadSolicitada(),cotizacionProductoRequest.getCantidadCotizada(),cotizacionProductoRequest.getPrecioUnitario());
                cotizacionProductoRepository.save(cotizacionProductoNew);
            }
        }
        
        return new ObjectResponse(Boolean.TRUE, null, null);
    }
    

    public ObjectResponse aprobar(CotizacionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();
        
        Optional<Cotizacion> optionalCotizacion = cotizacionRepository.findById(request.getId());
        if (optionalCotizacion.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró la cotización", null);
        }
        
        Optional<Solicitud> optionalSolicitud = solicitudRepository.findById(optionalCotizacion.get().getSolicitudProveedor().getSolicitud().getId());
        if (optionalSolicitud.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró la solicitud de la cotización", null);
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
        
        EstadoCotizacion estadoCotizacion;
        Optional<EstadoCotizacion> optionalEstadoCotizacion = estadoCotizacionRepository.findById(Constantes.EstadoCotizacion.APROBADO);
        if (optionalEstadoCotizacion.isPresent()) {
            estadoCotizacion = optionalEstadoCotizacion.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado de cotización asignado",
                    null
            );
        }
        EstadoSolicitud estadoSolicitud;
        Optional<EstadoSolicitud> optionalEstadoSolicitud = estadoSolicitudRepository.findById(Constantes.EstadoSolicitud.FINALIZADO);
        if (optionalEstadoSolicitud.isPresent()) {
            estadoSolicitud = optionalEstadoSolicitud.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado de solicitud asignado",
                    null
            );
        }

        //ACTUALIZA COTIZACION
        Cotizacion cotizacion = optionalCotizacion.get();
        cotizacion.setEstado(estadoCotizacion);
        cotizacion.setUsuarioEstado(usuario);
        cotizacionRepository.save(cotizacion);

        //ACTUALIZA SOLICITUD
        Solicitud solicitud = optionalSolicitud.get();
        solicitud.setEstado(estadoSolicitud);
        solicitud.setFechaFinalizado(new Date());
        solicitud.setUsuarioEstado(usuario);
        solicitudRepository.save(solicitud);

        //CREA UN PEDIDO
        Pedido pedido=new Pedido();
        pedido.setProveedor(cotizacion.getSolicitudProveedor().getProveedor());
        pedido.setDescripcion(solicitud.getDescripcion());
        pedido.setObservacion(cotizacion.getComentario());
        pedido.setMontoTotal(cotizacion.getMonto());
        pedido.setUsuarioCreacion(usuario);
        pedido.setUsuarioEstado(usuario);
        pedido.setFechaRegistro(new Date());
        pedido=pedidoRepository.save(pedido);

        List<CotizacionProducto> cp= cotizacionProductoRepository.findByFilter(cotizacion.getId());
        for (CotizacionProducto cpTemp: cp){
            PedidoProducto pp=PedidoProducto.builder()
                    .pedido(pedido)
                    .producto(cpTemp.getProducto())
                    .cantidad(cpTemp.getCantidadCotizada())
                    .monto(cpTemp.getPrecioUnitario())
                    .build();
            pedidoProductoRepository.save(pp);
        }

        return new ObjectResponse<>(Boolean.TRUE, null, null);
    }
    
    public ObjectResponse archivar(CotizacionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();
        
        Optional<Cotizacion> optionalCotizacion = cotizacionRepository.findById(request.getId());
        if (optionalCotizacion.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró la cotización", null);
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
        
        EstadoCotizacion estado;
        Optional<EstadoCotizacion> optionalEstado = estadoCotizacionRepository.findById(Constantes.EstadoCotizacion.ARCHIVADO);
        if (optionalEstado.isPresent()) {
            estado = optionalEstado.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado asignado",
                    null
            );
        }
        
        Cotizacion cotizacion = optionalCotizacion.get();
        cotizacion.setEstado(estado);
        cotizacion.setUsuarioEstado(usuario);
        cotizacionRepository.save(cotizacion);
        return new ObjectResponse<>(Boolean.TRUE, null, null);
    }
}
