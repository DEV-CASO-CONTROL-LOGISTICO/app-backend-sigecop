package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.Obligacion;

/**
 *
 * @author Diego
 */

@Repository
public interface ObligacionRepository extends JpaRepository<Obligacion, Integer> {
    @Query("select o from Obligacion o "
            + "where o.activo = true and (:descripcion is null or o.descripcion like %:descripcion%) "
            + "and (:pedidoId is null or (o.pedido.activo = true and o.pedido.id = :pedidoId)) "
            + "and (:codigo is null or o.codigo like %:codigo%) "
            + "and (:tipoId is null or o.tipo.id = :tipoId) "
            + "order by o.id desc")
    List<Obligacion> findByFilter(
            @Param("pedidoId") Integer pedidoId,
            @Param("codigo") String codigo,
            @Param("tipoId") Integer tipoId,
            @Param("descripcion") String descripcion
    );
}
