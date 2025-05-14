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
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.CotizacionProductoResponse;
import sigecop.backend.gestion.dto.CotizacionRequest;
import sigecop.backend.gestion.dto.CotizacionResponse;
import sigecop.backend.gestion.model.Cotizacion;
import sigecop.backend.gestion.model.CotizacionProducto;
import sigecop.backend.gestion.model.EstadoCotizacion;
import sigecop.backend.gestion.model.EstadoSolicitud;
import sigecop.backend.gestion.model.Solicitud;
import sigecop.backend.gestion.repository.CotizacionProductoRepository;
import sigecop.backend.gestion.repository.CotizacionRepository;
import sigecop.backend.gestion.repository.EstadoCotizacionRepository;
import sigecop.backend.gestion.repository.EstadoSolicitudRepository;
import sigecop.backend.gestion.repository.SolicitudRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.Constantes;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;

/**
 *
 * @author Moises_F16.7.24
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
    private CotizacionProductoRepository cotizacionProductoRepository;
    
    public CotizacionService(CotizacionRepository _cotizacionRepository) {
        super(CotizacionResponse.class, _cotizacionRepository);
        this.cotizacionRepository = _cotizacionRepository;
    }
    
    @Override
    public List<Cotizacion> listBase(CotizacionRequest filter) {
        return cotizacionRepository.findByFilter(
                filter.getSolicitudId(),
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
        //IMPLEMENTAR AQUI
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
    
    @Override
    public ObjectResponse<Cotizacion> recordToEntityNew(CotizacionRequest request) {
        //IMPLEMENTAR AQUI
        return new ObjectResponse<>(Boolean.TRUE, null, new Cotizacion());
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
        
        Cotizacion cotizacion = optionalCotizacion.get();
        cotizacion.setEstado(estadoCotizacion);
        cotizacion.setUsuarioEstado(usuario);
        cotizacionRepository.save(cotizacion);
        
        Solicitud solicitud = optionalSolicitud.get();
        solicitud.setEstado(estadoSolicitud);
        solicitud.setFechaFinalizado(new Date());
        solicitud.setUsuarioEstado(usuario);
        solicitudRepository.save(solicitud);
        
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
