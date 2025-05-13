package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer>{
    
    @Query("select s from Solicitud s "
        + "where s.activo = true and (:descripcion is null or s.descripcion like %:descripcion%) "
        + "and (:codigo is null or s.codigo like %:codigo%) "
        + "and (:estadoId is null or s.estado.id = :estadoId) "
        + "order by s.id desc")
    List<Solicitud> findByFilter(
            @Param("codigo") String codigo,
            @Param("descripcion") String descripcion,
            @Param("estadoId") Integer estadoId
    );

}
