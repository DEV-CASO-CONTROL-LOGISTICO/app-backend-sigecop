
package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.Pedido;

/**
 *
 * @author Diego Poma
 */

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    
    @Query("select p from Pedido p "
            + "where p.activo = true "
            + "and (:proveedorRazonSocial is null or p.proveedor.razonSocial LIKE %:proveedorRazonSocial%) "
            + "and (:codigo is null or p.codigo like %:codigo%) "
            + "and (:descripcion is null or p.descripcion like %:descripcion%)"
            + "and (:estadoId is null or p.estado.id = :estadoId) "
            + "order by p.id desc")
    List<Pedido> findByFilter(
            @Param("proveedorRazonSocial") String proveedorRazonSocial,
            @Param("codigo") String codigo,
            @Param("descripcion") String descripcion,
            @Param("estadoId") Integer estadoId
    );
}
