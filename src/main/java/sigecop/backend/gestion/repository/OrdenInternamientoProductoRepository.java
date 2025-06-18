package sigecop.backend.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.OrdenInternamientoProducto;

import java.util.List;

@Repository
public interface OrdenInternamientoProductoRepository extends JpaRepository<OrdenInternamientoProducto, Integer> {

    @Query("Select op from OrdenInternamientoProducto op "
            + "where op.activo = true "
            + "and (:ordenInternamientoId is null or op.ordenInternamiento.id = :ordeninternamientoId) "
            + "order by op.id desc")
    List<OrdenInternamientoProducto> findByFilter(@Param("ordenInternamientoId") Integer ordenInternamientoId);
}
