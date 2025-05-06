package ccl.securitybackend.master.service;

import ccl.securitybackend.master.dto.ProveedorRequest;
import ccl.securitybackend.master.dto.ProveedorResponse;
import ccl.securitybackend.master.model.Proveedor;
import ccl.securitybackend.master.repository.ProveedorRepository;
import ccl.securitybackend.security.model.Usuario;
import ccl.securitybackend.security.repository.UsuarioRepository;
import ccl.securitybackend.utils.ObjectResponse;
import ccl.securitybackend.utils.generic.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements ServiceBase<ProveedorResponse,ProveedorRequest> {

    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<ProveedorResponse> list(ProveedorRequest filter) {
        return ProveedorResponse.fromEntities(proveedorRepository.findByFilter(
                filter.getRuc(),filter.getRazonSocial(),filter.getNombreComercial()
        ));
    }

    @Override
    public ObjectResponse<ProveedorResponse> find(ProveedorRequest request) {
        Optional<Proveedor> result = proveedorRepository.findById(request.getId());
        if (!result.isPresent()) {
            return new ObjectResponse(Boolean.FALSE,"No se encontró el proveedor",null);
        }
        return new ObjectResponse(Boolean.TRUE,null,ProveedorResponse.fromEntity(result.get()));
    }

    @Override
    public ObjectResponse<ProveedorResponse> save(ProveedorRequest proveedorRequest) {
        Proveedor proveedor;
        if (proveedorRequest.getId() != null) {
            Optional<Proveedor> optionalProveedor = proveedorRepository.findById(proveedorRequest.getId());
            if (optionalProveedor.isEmpty()) {
                return new ObjectResponse(Boolean.FALSE,"No se encontró el proveedor a editar",null);
            }
            proveedor = optionalProveedor.get();
            proveedor.setRuc(proveedorRequest.getRuc());
            proveedor.setRazonSocial(proveedorRequest.getRazonSocial());
            proveedor.setNombreComercial(proveedorRequest.getNombreComercial());
            proveedor.setDireccion(proveedorRequest.getDireccion());
            proveedor.setTelefono(proveedorRequest.getTelefono());
            proveedor.setCorreo(proveedorRequest.getCorreo());
        } else {
            proveedor = new Proveedor(
                    proveedorRequest.getId(),
                    proveedorRequest.getRuc(),
                    proveedorRequest.getRazonSocial(),
                    proveedorRequest.getNombreComercial(),
                    proveedorRequest.getDireccion(),
                    proveedorRequest.getTelefono(),
                    proveedorRequest.getCorreo()
            );
        }
        proveedor = proveedorRepository.save(proveedor);
        return new ObjectResponse(Boolean.TRUE,null,ProveedorResponse.fromEntity(proveedor));
    }

    @Override
    public ObjectResponse<ProveedorResponse> delete(ProveedorRequest request) {
        List<Usuario> resultUsuarios=usuarioRepository.findByProveedor(request.getId());
        if(resultUsuarios==null || resultUsuarios.isEmpty()){
            Optional<Proveedor> optionalProveedor = proveedorRepository.findById(request.getId());
            if (optionalProveedor.isEmpty()) {
                return new ObjectResponse(Boolean.FALSE,"No se encontró al proveedor a eliminar",null);
            }
            Proveedor proveedor = optionalProveedor.get();
            proveedor.setActivo(Boolean.FALSE);
            proveedorRepository.save(proveedor);
            return new ObjectResponse(Boolean.TRUE,null,ProveedorResponse.fromEntity(proveedor));
        }else{
            return new ObjectResponse(Boolean.FALSE,"No puede eliminar mientras haya tenga usuarios asignados",null);
        }
    }

}
