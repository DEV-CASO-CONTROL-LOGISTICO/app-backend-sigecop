
package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sigecop.backend.gestion.model.PedidoProducto;

/**
 *
 * @author Diego Poma
 */
public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, Integer>{
    
    @Query("select pp from PedidoProducto pp "
            + "where pp.activo = true "
            + "and (:pedidoId is null or pp.pedido.id = :pedidoId) "
            + "order by pp.id desc")
    List<PedidoProducto> findByFilter(@Param("pedidoId") Integer pedidoId);
}
