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
import sigecop.backend.gestion.dto.SolicitudProductoRequest;
import sigecop.backend.gestion.dto.SolicitudRequest;
import sigecop.backend.gestion.dto.SolicitudResponse;
import sigecop.backend.gestion.model.EstadoSolicitud;
import sigecop.backend.gestion.model.Solicitud;
import sigecop.backend.gestion.model.SolicitudProducto;
import sigecop.backend.gestion.model.SolicitudProveedor;
import sigecop.backend.gestion.repository.EstadoSolicitudRepository;
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
import sigecop.backend.utils.Constantes;
import sigecop.backend.gestion.dto.SolicitudProveedorRequest;
import sigecop.backend.gestion.dto.SolicitudProveedorResponse;
/**
 *
 * @author alexChuky
 */
public class SolicitudProveedorService extends ServiceGeneric<SolicitudProveedorResponse, SolicitudProveedorRequest, SolicitudProveedor>{
    private final SolicitudProveedorRepository solicitudProveedorRepository;
    @Autowired
    private SolicitudProductoRepository solicitudProductoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private EstadoSolicitudRepository estadoSolicitudRepository;
    
    public SolicitudProveedorService(SolicitudProveedorRepository _solicitudProveedorRepository) {
        super(SolicitudProveedorResponse.class, _solicitudProveedorRepository);
        this.solicitudProveedorRepository = _solicitudProveedorRepository;
    }
        
    @Override
    public List<SolicitudProveedor> listBase(SolicitudProveedorRequest filter) {
        return solicitudProveedorRepository.findByFilters(
                filter.getSolicitudId()
        );
    }
    
    @Override
    public ObjectResponse<SolicitudProveedorResponse> postFind(SolicitudProveedorResponse response) {
         return new ObjectResponse<>(Boolean.TRUE, null, response);
    }
    
    //@Override
    public List<SolicitudProveedor> listSolicitudProveedorByProveedor(SolicitudProveedorRequest filter) {
        
         List<SolicitudProveedor> response = new ArrayList<SolicitudProveedor>();
         if (filter != null && filter.getProveedorId() != null) { 
            List<SolicitudProveedor> solicitudProveedor = solicitudProveedorRepository.listSolicitudProveedorByProveedor(filter.getProveedorId());
            response = solicitudProveedorRepository.listSolicitudProveedorByProveedor(filter.getProveedorId());
         }
        
        return response;
    }
    
    @Override
    public ObjectResponse<SolicitudProveedor> recordToEntityNew(SolicitudProveedorRequest request) {
        Proveedor proveedor = new Proveedor();
        Solicitud solicitud = new Solicitud();
        
        SolicitudProveedor entity = SolicitudProveedor.builder()
                .proveedor(proveedor)
                .solicitud(solicitud)
                .build();
        
        return new ObjectResponse<>(Boolean.TRUE, null, entity);
    }
    
    
     @Override
    public ObjectResponse<SolicitudProveedor> recordToEntityEdit(SolicitudProveedor entity, SolicitudProveedorRequest request) {
        return new ObjectResponse(Boolean.TRUE, null, null);
    }
}
