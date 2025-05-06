/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ccl.securitybackend.security.repository;

import ccl.securitybackend.master.model.Proveedor;
import ccl.securitybackend.security.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author Moises_F16.7.24
 */
public interface RolRepository extends JpaRepository<Rol, Integer>  {

    @Query("select r from Rol r where r.activo = true")
    List<Rol> findByFilter();

}
