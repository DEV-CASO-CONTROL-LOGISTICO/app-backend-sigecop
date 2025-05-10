/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sigecop.backend.security.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sigecop.backend.security.model.Permiso;

/**
 *
 * @author Moises_F16.7.24
 */
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    @Query("select pe from Permiso pe where pe.activo = true "
            + "and (:rolId is null or pe.rol.id = :rolId) "
            + "and (:paginaId is null or pe.pagina.id = :paginaId) "
            + "order by pe.id desc")
    List<Permiso> findByFilter(@Param("rolId") Integer rolId, @Param("paginaId") Integer paginaId);

}
