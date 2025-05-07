package ccl.securitybackend.master.service;

import ccl.securitybackend.master.dto.ProveedorRequest;
import ccl.securitybackend.master.dto.ProveedorResponse;
import ccl.securitybackend.master.model.Proveedor;
import ccl.securitybackend.master.repository.ProveedorRepository;
import ccl.securitybackend.security.model.Usuario;
import ccl.securitybackend.security.repository.UsuarioRepository;
import ccl.securitybackend.utils.ObjectResponse;
import ccl.securitybackend.utils.generic.ServiceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService extends ServiceGeneric<ProveedorResponse,ProveedorRequest,Proveedor> {


    private final ProveedorRepository proveedorRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public ProveedorService(ProveedorRepository _proveedorRepository) {
        super(ProveedorResponse.class,_proveedorRepository);
        this.proveedorRepository = _proveedorRepository;
    }

    @Override
    public List<Proveedor> listBase(ProveedorRequest filter) {
        return proveedorRepository.findByFilter(
                filter.getRuc(),
                filter.getRazonSocial(),
                filter.getNombreComercial()
        );
    }

    @Override
    public ObjectResponse<Proveedor> recordToEntityEdit( Proveedor entity,ProveedorRequest request) {
        entity.setRuc(request.getRuc());
        entity.setRazonSocial(request.getRazonSocial());
        entity.setNombreComercial(request.getNombreComercial());
        entity.setDireccion(request.getDireccion());
        entity.setTelefono(request.getTelefono());
        entity.setCorreo(request.getCorreo());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Proveedor> recordToEntityNew(ProveedorRequest request) {
        return new ObjectResponse<>(Boolean.TRUE,null,new Proveedor(
                request.getId(),
                request.getRuc(),
                request.getRazonSocial(),
                request.getNombreComercial(),
                request.getDireccion(),
                request.getTelefono(),
                request.getCorreo()
        ));
    }

    @Override
    public ObjectResponse validateDelete(ProveedorRequest request){
        List<Usuario> resultUsuarios=usuarioRepository.findByProveedor(request.getId());
        return (resultUsuarios==null || resultUsuarios.isEmpty())?
                new ObjectResponse(Boolean.TRUE,null,null):
                new ObjectResponse(Boolean.FALSE,"No puede eliminar mientras tenga usuarios asignados",null);
    }

}
