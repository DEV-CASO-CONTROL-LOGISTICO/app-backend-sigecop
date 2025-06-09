
package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.EstadoPedido;

/**
 *
 * @author Diego Poma
 */
@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer>{
    
    @Query("select e from EstadoPedido e "
            + "where e.activo = true "
            + "order by e.id desc")
    List<EstadoPedido> findByFilter();
}
