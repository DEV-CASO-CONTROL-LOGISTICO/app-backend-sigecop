/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ccl.securitybackend.security.repository;

import ccl.securitybackend.security.model.Pagina;
import ccl.securitybackend.security.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Moises_F16.7.24
 */
@Repository
public interface PaginaRepository extends JpaRepository<Usuario, Integer> {

    @Query("select pe.pagina from Permiso pe where pe.rol.id = :rolId")
    List<Pagina> listForRol(@Param("rolId") Integer rolId);

}
