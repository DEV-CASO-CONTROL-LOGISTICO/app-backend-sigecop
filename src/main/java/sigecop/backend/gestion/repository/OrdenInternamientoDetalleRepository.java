package sigecop.backend.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.EstadoSolicitud;
import sigecop.backend.gestion.model.OrdenInternamientoDetalle;

import java.util.List;

@Repository
public interface OrdenInternamientoDetalleRepository extends JpaRepository<OrdenInternamientoDetalle, Integer> {

    @Query("select o from OrdenInternamientoDetalle o "
            + "where o.activo = true "
            + "and (:ordenInternamientoId is null or o.ordenInternamiento.id = :ordenInternamientoId) "
            + "order by o.id desc")
    List<OrdenInternamientoDetalle> findByFilter(@Param("ordenInternamientoId") Integer ordenInternamientoId);

}
