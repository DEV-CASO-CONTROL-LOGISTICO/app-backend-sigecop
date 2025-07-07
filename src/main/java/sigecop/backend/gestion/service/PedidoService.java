
package sigecop.backend.gestion.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sigecop.backend.gestion.dto.*;
import sigecop.backend.gestion.model.*;
import sigecop.backend.gestion.repository.EstadoPedidoRepository;
import sigecop.backend.gestion.repository.PedidoProductoRepository;
import sigecop.backend.gestion.repository.PedidoRepository;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.master.model.TipoInternamiento;
import sigecop.backend.master.repository.ProveedorRepository;
import sigecop.backend.master.repository.TipoInternamientoRepository;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.Constantes;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import sigecop.backend.gestion.repository.EstadoObligacionRepository;
import sigecop.backend.master.model.TipoObligacion;
import sigecop.backend.master.repository.TipoObligacionRepository;

/**
 *
 * @author Diego Poma
 */
@Service
public class PedidoService extends ServiceGeneric<PedidoResponse, PedidoRequest, Pedido>{
    
    private final PedidoRepository pedidoRepository;
    private final OrdenInternamientoService ordenInternamientoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;
    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;
    @Autowired
    private TipoInternamientoRepository tipoInternamientoRepository;
    @Autowired
    private ObligacionService obligacionService;
    @Autowired
    private TipoObligacionRepository tipoObligacionRepository;
    @Autowired
    private EstadoObligacionRepository estadoObligacionRepository;
        
    public PedidoService(PedidoRepository _pedidoRepository,OrdenInternamientoService _ordenInternamientoService) {
        super(PedidoResponse.class, _pedidoRepository);
        this.pedidoRepository = _pedidoRepository;
        this.ordenInternamientoService = _ordenInternamientoService;
    }

    @Override
    public List<Pedido> listBase(PedidoRequest filter) {
        return pedidoRepository.findByFilter(
                filter.getProveedorRazonSocial(),
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

    public ObjectResponse darConformidad(PedidoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        Optional<Pedido> optionalPedido = pedidoRepository.findById(request.getId());
        if (optionalPedido.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el pedido", null);
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

        EstadoPedido estadoPedidoConforme;
        Optional<EstadoPedido> optionalEstadoPedido = estadoPedidoRepository.findById(Constantes.EstadoPedido.CON_CONFORMIDAD);
        if (optionalEstadoPedido.isPresent()) {
            estadoPedidoConforme = optionalEstadoPedido.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado de pedido con conformidad",
                    null
            );
        }

        List<TipoInternamiento> listTipoInternamiento = tipoInternamientoRepository.findByFilter();
        listTipoInternamiento=listTipoInternamiento==null?new ArrayList<>():listTipoInternamiento;
        Integer tipoInternamientoId = listTipoInternamiento.stream()
                .filter(TipoInternamiento::getValorDefecto)
                .map(TipoInternamiento::getId)
                .findFirst()
                .orElse(null);
        if (tipoInternamientoId==null ) {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró un tipo de internamiento por defecto",
                    null
            );
        }
        
        List<TipoObligacion> listTipoObligacion = tipoObligacionRepository.findByFilter();
        listTipoObligacion=listTipoObligacion==null?new ArrayList<>():listTipoObligacion;
        Integer tipoObligacionId = listTipoObligacion.stream()
                .filter(TipoObligacion::getValorDefecto)
                .map(TipoObligacion::getId)
                .findFirst()
                .orElse(null);
        if (tipoObligacionId==null ) {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró un tipo de obligacion por defecto",
                    null
            );
        }
        
        EstadoObligacion estadoObligacion;
        Optional<EstadoObligacion> optionalEstado = estadoObligacionRepository.findById(Constantes.EstadoObligacion.GENERADO_AUTOMATICO);
        if (optionalEstado.isPresent()) {
            estadoObligacion = optionalEstado.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado GENERADO AUTOMATICO",
                    null
            );
        }
        
        //ACTUALIZA PEDIDO
        Pedido pedido = optionalPedido.get();
        pedido.setEstado(estadoPedidoConforme);
        pedido.setUsuarioEstado(usuario);
        pedidoRepository.save(pedido);

        List<PedidoProducto> productos=pedidoProductoRepository.findByFilter(pedido.getId());

        //CREA ORDEN INTERNAMIENTO
        ObjectResponse<OrdenInternamientoResponse> oiReponse= ordenInternamientoService.save(OrdenInternamientoRequest
                .builder()
                .tipoId(tipoInternamientoId)
                .pedidoId(pedido.getId())
                .descripcion(pedido.getDescripcion())
                .detalles(productos
                        .stream()
                        .map(obj->OrdenInternamientoDetalleRequest
                                .builder()
                                .productoId(obj.getProducto().getId())
                                .cantidad(obj.getCantidad())
                                .build())
                        .toList())
                .build());

        //CREAR OBLIGACION AUTOMATICA
         ObjectResponse<ObligacionResponse> oReponse= obligacionService.save(ObligacionRequest
                .builder()
                .pedidoId(pedido.getId())
                .tipoId(tipoObligacionId)
                .estadoId(estadoObligacion.getId())
                .descripcion(pedido.getDescripcion())
                .monto(pedido.getMontoTotal())
                .build());        
        
        return new ObjectResponse<>(Boolean.TRUE, null, null);
    }

    public ObjectResponse devolver(PedidoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        Optional<Pedido> optionalPedido = pedidoRepository.findById(request.getId());
        if (optionalPedido.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el pedido", null);
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

        EstadoPedido estadoPedidoDevuelto;
        Optional<EstadoPedido> optionalEstadoPedido = estadoPedidoRepository.findById(Constantes.EstadoPedido.DEVUELTO);
        if (optionalEstadoPedido.isPresent()) {
            estadoPedidoDevuelto = optionalEstadoPedido.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado de pedido devuelto",
                    null
            );
        }

        //ACTUALIZA PEDIDO
        Pedido pedido = optionalPedido.get();
        pedido.setEstado(estadoPedidoDevuelto);
        pedido.setObservacionEnvio(request.getObservacionEnvio());
        pedido.setUsuarioEstado(usuario);
        pedidoRepository.save(pedido);

        return new ObjectResponse<>(Boolean.TRUE, null, null);
    }
    
    public List<PedidoResponse> listPedidoByProveedor(PedidoRequest filter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();
        Optional<Usuario> user= usuarioRepository.findById(userId);
        user.ifPresent(usuario -> filter.setProveedorId(usuario.getProveedor().getId()));

        List<PedidoResponse> response = new ArrayList<PedidoResponse>();
        
        if (filter != null && filter.getProveedorId() != null) {
            List<Pedido> pedidos = pedidoRepository.findByProveedor(
                    filter.getProveedorId(),
                    filter.getCodigo(),
                    filter.getDescripcion(),
                    filter.getEstadoId()
            );
            pedidos=pedidos != null ? pedidos : new ArrayList<>();
            for(Pedido sp: pedidos){
                PedidoResponse sr=PedidoResponse.fromEntity(sp,PedidoResponse.class);
                response.add(sr);
            }
        }
        return response;
    }
    
    public ObjectResponse enviarPedido(PedidoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        Optional<Pedido> optionalPedido = pedidoRepository.findById(request.getId());
        if (optionalPedido.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el pedido", null);
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

        EstadoPedido estadoPedidoEnviado;
        Optional<EstadoPedido> optionalEstadoPedido = estadoPedidoRepository.findById(Constantes.EstadoPedido.ENVIADO);
        if (optionalEstadoPedido.isPresent()) {
            estadoPedidoEnviado = optionalEstadoPedido.get();
        } else {
            return new ObjectResponse<>(
                    Boolean.FALSE,
                    "No se encontró el estado de pedido enviado",
                    null
            );
        }
        
        //ESTADO CAMBIA A PEDIDO
        Pedido pedido = optionalPedido.get();
        pedido.setNumeroFactura(request.getNumeroFactura());
        pedido.setSerieGuia(request.getSerieGuia());
        pedido.setNumeroGuia(request.getNumeroGuia());
        pedido.setFechaEntrega(request.getFechaEntrega());
        pedido.setObservacionEnvio(request.getObservacionEnvio());
        pedido.setFechaRegistro(request.getFechaRegistro());
        pedido.setEstado(estadoPedidoEnviado);
        pedido.setUsuarioEstado(usuario);
        pedidoRepository.save(pedido);
        
        return new ObjectResponse<>(Boolean.TRUE, null, null);
    }
    
    public ObjectResponse guardarArchivo(MultipartFile archivo,String idPedidoEntrada,Integer tipo) throws IOException {
        
        Integer idPedido = Integer.parseInt(idPedidoEntrada);
        String ruta;
        String nombre ="";
        if (tipo == Constantes.TipoArchivo.GUIA){
            nombre = idPedidoEntrada + "-" + "GUIA" + ".pdf";
            ruta = Constantes.RutaUpload.DIR_GUIA;
        }else{
            if (tipo == Constantes.TipoArchivo.FACTURA){
                 nombre = idPedidoEntrada + "-" + "FACTURA" + ".pdf";
                 ruta = Constantes.RutaUpload.DIR_FACTURA;
            }else {
                 nombre = idPedidoEntrada + "-" + "GUIA" + ".pdf";
                 ruta = Constantes.RutaUpload.DIR_GUIA;
            }
        }
        
        // Crear el directorio si no existe
        Path uploadPath = Paths.get(ruta);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
 
        String filename = nombre;

        // Crear la ruta completa del archivo
        Path filePath = uploadPath.resolve(filename);

        // Guardar el archivo
        archivo.transferTo(filePath); // Guarda el archivo
        
        return new ObjectResponse<>(Boolean.TRUE, null, null);
    }
    
    public ObjectResponse downloadGuia(PedidoRequest request) throws IOException {
        
        Optional<Pedido> optionalPedido = pedidoRepository.findById(request.getId());
        if (optionalPedido.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el pedido", null);
        } 
        Pedido pedido = optionalPedido.get();        
        
        String ruta;
        
        Integer idPedido = pedido.getId();
        String numero = pedido.getNumeroGuia();
        String nombre = idPedido + "-" + numero + ".pdf";
        
        ruta = Constantes.RutaUpload.DIR_GUIA;
        
        // Crear el directorio si no existe
        Path downloadPath = Paths.get(ruta +"/"+ nombre);
        
        System.out.println("Ruta buscada: " + ruta + nombre);
        
        if (!Files.exists(downloadPath)) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontro el archivo", null);
        }
            
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(downloadPath));
        
        return new ObjectResponse<>(Boolean.TRUE, null, resource);
    }
    
    public ObjectResponse downloadFactura(PedidoRequest request) throws IOException {
        
        Optional<Pedido> optionalPedido = pedidoRepository.findById(request.getId());
        if (optionalPedido.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el pedido", null);
        } 
        Pedido pedido = optionalPedido.get();        
        
        String ruta;
        
        Integer idPedido = pedido.getId();
        String numero = pedido.getNumeroFactura();
        String nombre = idPedido + "-" + numero + ".pdf";
        
        ruta = Constantes.RutaUpload.DIR_FACTURA;
        
        // Crear el directorio si no existe
        Path downloadPath = Paths.get(ruta +"/"+ nombre);
        
        System.out.println("Ruta buscada: " + ruta + nombre);
        
        if (!Files.exists(downloadPath)) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontro el archivo", null);
        }
            
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(downloadPath));
        
        return new ObjectResponse<>(Boolean.TRUE, null, resource);
    }    
    
    public ObjectResponse<Resource> obtenerGuia(Integer pedidoId) throws IOException {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(pedidoId);
        if (optionalPedido.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el pedido", null);
        }

        Pedido pedido = optionalPedido.get();
        String nombreArchivo = pedidoId + "-" + pedido.getNumeroGuia() + ".pdf";
        Path filePath = Paths.get(Constantes.RutaUpload.DIR_GUIA, nombreArchivo);

        if (!Files.exists(filePath)) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el archivo de guía", null);
        }

        Resource resource = new InputStreamResource(Files.newInputStream(filePath));
        return new ObjectResponse<>(Boolean.TRUE, null, resource);
    }

    public ObjectResponse<Resource> obtenerFactura(Integer pedidoId) throws IOException {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(pedidoId);
        if (optionalPedido.isEmpty()) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el pedido", null);
        }

        Pedido pedido = optionalPedido.get();
        String nombreArchivo = pedidoId + "-" + pedido.getNumeroFactura() + ".pdf";
        Path filePath = Paths.get(Constantes.RutaUpload.DIR_FACTURA, nombreArchivo);

        if (!Files.exists(filePath)) {
            return new ObjectResponse<>(Boolean.FALSE, "No se encontró el archivo de factura", null);
        }

        Resource resource = new InputStreamResource(Files.newInputStream(filePath));
        return new ObjectResponse<>(Boolean.TRUE, null, resource);
    }
}
