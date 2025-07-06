package sigecop.backend.master.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sigecop.backend.master.model.TipoObligacion;

/**
 *
 * @author Diego
 */

@Repository
public interface TipoObligacionRepository extends JpaRepository<TipoObligacion, Integer> {

    @Query("select t from TipoObligacion t "
            + "where t.activo = true "
            + "order by t.id desc")
    List<TipoObligacion> findByFilter();
    
}
