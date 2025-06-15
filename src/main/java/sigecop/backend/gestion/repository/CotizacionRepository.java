
package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.Cotizacion;

/**
 *
 * @author Moises_F16.7.24
 */
@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {

    @Query("select c from Cotizacion c "
            + "where c.activo = true "
            + "and (:solicitudId is null or (c.solicitudProveedor.activo = true and c.solicitudProveedor.solicitud.id = :solicitudId)) "
            + "and (:codigo is null or c.codigo like %:codigo%) "
            + "and (:estadoId is null or c.estado.id = :estadoId) "
            + "order by c.id desc")
    List<Cotizacion> findByFilter(
            @Param("solicitudId") Integer solicitudId,
            @Param("codigo") String codigo,
            @Param("estadoId") Integer estadoId
    );

}
