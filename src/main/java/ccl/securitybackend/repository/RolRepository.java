/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ccl.securitybackend.repository;

import ccl.securitybackend.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Moises_F16.7.24
 */
public interface RolRepository extends JpaRepository<Rol, Integer>  {
    
}
