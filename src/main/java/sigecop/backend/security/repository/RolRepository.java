/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sigecop.backend.security.repository;

import sigecop.backend.security.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author Moises_F16.7.24
 */
public interface RolRepository extends JpaRepository<Rol, Integer>  {

    @Query("select r from Rol r where r.activo = true")
    List<Rol> findByFilter();

}
