package sigecop.backend.master.repository;

import sigecop.backend.master.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @Query("select p from Proveedor p "
            + "where p.activo = true and (:ruc is null or p.ruc like %:ruc%) "
            + "and (:razonSocial is null or p.razonSocial like %:razonSocial%) "
            + "and (:nombreComercial is null or p.nombreComercial like %:nombreComercial%) "
            + "order by p.id desc")
    List<Proveedor> findByFilter(
            @Param("ruc") String ruc,
            @Param("razonSocial") String razonSocial,
            @Param("nombreComercial") String nombreComercial
    );

}
