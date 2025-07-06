package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.EstadoObligacion;


/**
 *
 * @author Diego
 */

@Repository
public interface EstadoObligacionRepository extends JpaRepository<EstadoObligacion, Integer>{
    @Query("select e from EstadoObligacion e "
            + "where e.activo = true "
            + "order by e.id desc")
    List<EstadoObligacion> findByFilter();
}
