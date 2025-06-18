package sigecop.backend.gestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.Producto;
import sigecop.backend.utils.AuditBase;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orden_internamiento_producto", schema = "gestion")
public class OrdenInternamientoProducto extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orden_internamiento_id", referencedColumnName = "id")
    private OrdenInternamiento ordenInternamiento;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;
}
