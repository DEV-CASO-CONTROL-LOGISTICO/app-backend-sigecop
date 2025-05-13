package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.SolicitudProducto;

/**
 *
 * @author Diego Poma
 */
@Repository
public interface SolicitudProductoRepository extends JpaRepository<SolicitudProducto, Integer>{
    @Query("select s from SolicitudProducto s "
         + "where s.activo = true and (:solicitudId is null or s.solicitud.id = :solicitudId) "
         + "order by s.id desc")
    List<SolicitudProducto> findByFilter(
            @Param("solicitudId") Integer solicitudId
    );
}
