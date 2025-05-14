/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sigecop.backend.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.gestion.model.Cotizacion;

/**
 *
 * @author Moises_F16.7.24
 */
@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {

    @Query("select c from Cotizacion c "
            + "where c.activo = true "
            + "and (:codigo is null or c.codigo like %:codigo%) "
            + "and (:estadoId is null or c.estado.id = :estadoId) "
            + "order by c.id desc")
    List<Cotizacion> findByFilter(
            @Param("codigo") String codigo,
            @Param("estadoId") Integer estadoId
    );

}
