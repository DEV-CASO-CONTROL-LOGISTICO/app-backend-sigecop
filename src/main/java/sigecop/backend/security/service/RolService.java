package sigecop.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import sigecop.backend.master.dto.ProveedorRequest;
import sigecop.backend.security.dto.RolRequest;
import sigecop.backend.security.dto.RolResponse;
import sigecop.backend.security.model.Permiso;
import sigecop.backend.security.model.Rol;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.security.model.Pagina;
import sigecop.backend.security.repository.PaginaRepository;
import sigecop.backend.security.repository.PermisoRepository;
import sigecop.backend.security.repository.RolRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import sigecop.backend.security.repository.UsuarioRepository;
import sigecop.backend.utils.ObjectResponse;
import sigecop.backend.utils.generic.ServiceGeneric;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

/**
 *
 * @author Moises_F16.7.24
 */
@Service
public class RolService extends ServiceGeneric<RolResponse, RolRequest, Rol> {

    private final RolRepository rolRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PermisoRepository permisoRepository;
    @Autowired
    PaginaRepository paginaRepository;

    public RolService(RolRepository _rolRepository) {
        super(RolResponse.class,_rolRepository);
        this.rolRepository = _rolRepository;
    }

    @Override
    public List<Rol> listBase(RolRequest filter) {
        return rolRepository.findByFilter(filter.getNombre());
    } 

    @Override
    public ObjectResponse<Rol> recordToEntityEdit(Rol entity, RolRequest request) {
        entity.setNombre(request.getNombre());
        entity.setCodigo(request.getCodigo());
        return new ObjectResponse<>(Boolean.TRUE,null,entity);
    }

    @Override
    public ObjectResponse<Rol> recordToEntityNew(RolRequest request) {
        return new ObjectResponse<>(Boolean.TRUE,null,new Rol(
                request.getId(),
                request.getCodigo(),
                request.getNombre()
        ));
    }

    @Override
    public ObjectResponse<RolResponse> postFind(RolResponse response) {
        if(response!=null && response.getId()!=null){
            List<Permiso> permisos=permisoRepository.findByFilter(response.getId(),null);
            permisos=permisos!=null?permisos: new ArrayList<>();
            response.setPaginas(permisos.stream()
                    .map(p->p.getPagina().getId())
                    .collect(Collectors.toList()));
        }
        return new ObjectResponse<>(Boolean.TRUE,null,response);
    }

    @Override
    public ObjectResponse postSave(RolRequest request,Rol entity){
        List<Permiso> permisosActuales=permisoRepository.findByFilter(entity.getId(),null);
        permisosActuales =permisosActuales!=null?permisosActuales:new ArrayList<>();
        for(Permiso permisoActual: permisosActuales){
            permisoActual.setActivo(Boolean.FALSE);
            permisoRepository.save(permisoActual);
        }
        for(Integer paginaId:request.getPaginas()){
            Optional<Pagina> optionalPagina = paginaRepository.findById(paginaId);
            if(optionalPagina.isEmpty()){
                return new ObjectResponse(Boolean.TRUE,"La página a la cual quiere dar permiso no existe",null);
            }

            Optional<Permiso> optionalPermiso = permisosActuales.stream()
                    .filter(p -> p.getPagina().getId().equals(paginaId))
                    .findFirst();
            if(optionalPermiso.isPresent()){
                Permiso permisoEdit=optionalPermiso.get();
                permisoEdit.setActivo(Boolean.TRUE);
                permisoRepository.save(permisoEdit);
            }else{
                Permiso permisoNew=new Permiso(null,entity,optionalPagina.get());
                permisoRepository.save(permisoNew);
            }
        }
        return new ObjectResponse(Boolean.TRUE,null,null);
    }

    @Override
    public ObjectResponse validateDelete(RolRequest request){
        List<Usuario> resultUsuarios=usuarioRepository.findByFilter(null,null,null,request.getId());
        return (resultUsuarios==null || resultUsuarios.isEmpty())?
                new ObjectResponse(Boolean.TRUE,null,null):
                new ObjectResponse(Boolean.FALSE,"No puede eliminar mientras tenga usuarios asignados",null);
    }

}
