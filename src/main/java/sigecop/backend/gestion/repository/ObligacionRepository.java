package sigecop.backend.gestion.repository;

import java.util.List;
import java.util.Optional;
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
    @Query("SELECT o FROM Obligacion o "
        + "LEFT JOIN o.pedido p "
        + "LEFT JOIN p.proveedor prov " // ¡Nuevo JOIN crítico!
        + "WHERE o.activo = true "
        + "AND (:descripcion IS NULL OR o.descripcion LIKE %:descripcion%) "
        + "AND (:estadoId IS NULL OR o.estado.id = :estadoId) "
        + "AND (:pedidoId IS NULL OR (p IS NOT NULL AND p.activo = true AND p.id = :pedidoId)) "
        + "AND (:codigo IS NULL OR o.codigo LIKE %:codigo%) "
        + "AND (:tipoId IS NULL OR o.tipo.id = :tipoId) "
        + "AND (:proveedorRazonSocial IS NULL OR "
        + "     (p IS NOT NULL AND prov.razonSocial LIKE %:proveedorRazonSocial%) OR "
        + "     (p IS NULL AND o.nombreUsuarioPago LIKE %:proveedorRazonSocial%)) "
        + "ORDER BY o.id DESC")
    List<Obligacion> findByFilter(
        @Param("estadoId") Integer estadoId,
        @Param("pedidoId") Integer pedidoId,
        @Param("codigo") String codigo,
        @Param("tipoId") Integer tipoId,
        @Param("descripcion") String descripcion,
        @Param("proveedorRazonSocial") String proveedorRazonSocial
    );
    
    @Query("select o from Obligacion o " +
       "left join fetch o.pedido p " +
       "left join fetch p.pedidoProducto pp " +
       "left join fetch pp.producto " +
       "where o.id = :id")
    Optional<Obligacion> findByIdWithPedidoAndProductos(@Param("id") Integer id);
   
}
