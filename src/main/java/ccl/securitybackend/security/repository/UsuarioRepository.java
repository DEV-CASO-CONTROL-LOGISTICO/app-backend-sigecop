/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ccl.securitybackend.security.repository;

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
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("select u from Usuario u where u.cuenta = :login and u.clave = :clave and u.activo = true")
    Usuario getUserForCredentials(@Param("login") String login, @Param("clave") String clave);

    @Query("select u from Usuario u "
            + "where u.activo = true and (:nombre is null or u.nombre like %:nombre%) "
            + "and (:apellidoPaterno is null or u.apellidoPaterno like %:apellidoPaterno%) "
            + "and (:apellidoMaterno is null or u.apellidoMaterno like %:apellidoMaterno%) "
            + "and (:rolId is null or u.rol.id = :rolId)")
    List<Usuario> findByFilter(
            @Param("nombre") String nombre,
            @Param("apellidoPaterno") String apellidoPaterno,
            @Param("apellidoMaterno") String apellidoMaterno,
            @Param("rolId") Integer rolId);

    @Query("select u from Usuario u " +
            "where u.proveedor != null and u.proveedor.id = :proveedorId and u.activo = true")
    List<Usuario> findByProveedor(
            @Param("proveedorId") Integer proveedorId
    );
}
