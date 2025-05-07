package ccl.securitybackend.master.repository;

import ccl.securitybackend.master.model.Categoria;
import ccl.securitybackend.master.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("select p from Producto p "
            + "where p.activo = true and (:categoriaId is null or p.categoria.id = :categoriaId) "
            + "and (:nombre is null or p.nombre like %:nombre%) "
            + "order by p.id desc")
    List<Producto> findByFilter(@Param("categoriaId") Integer categoriaId,@Param("nombre") String nombre);

}