package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.SolicitudProveedor;

/**
 *
 * @author Diego Poma
 */
@Repository
public interface SolicitudProveedorRepository extends JpaRepository<SolicitudProveedor, Integer>{
    @Query("select sp from SolicitudProveedor sp " +
           "where sp.activo = true and (:solicitudId is null or sp.solicitud.id = :solicitudId) " +
           "order by sp.id desc")
    List<SolicitudProveedor> findByFilters(
            @Param("solicitudId") Integer solicitudId
    );
    
    @Query("select sp from SolicitudProveedor sp " +
           "where sp.activo = true and (:proveedorId is null or sp.proveedor.id = :proveedorId) " +
           "order by sp.id desc")
    List<SolicitudProveedor> listSolicitudProveedorByProveedor(
            @Param("proveedorId") Integer proveedorId
    );

}
