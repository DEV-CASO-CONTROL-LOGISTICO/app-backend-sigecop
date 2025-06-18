package sigecop.backend.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.OrdenInternamiento;

import java.util.List;

@Repository
public interface OrdenInternamientoRepository extends JpaRepository<OrdenInternamiento, Integer> {

    @Query("select o from OrdenInternamiento o "
            + "where o.activo = true and (:descripcion is null or o.descripcion like %:descripcion%) "
            + "and (:codigo is null or o.codigo like %:codigo%) "
            + "and (:tipoId is null or o.tipo.id = :tipoId) "
            + "order by o.id desc")
    List<OrdenInternamiento> findByFilter(
            @Param("codigo") String codigo,
            @Param("tipoId") Integer tipoId,
            @Param("descripcion") String descripcion
    );

}
