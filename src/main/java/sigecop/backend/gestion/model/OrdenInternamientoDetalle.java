package sigecop.backend.gestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.Producto;
import sigecop.backend.master.model.TipoInternamiento;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.utils.AuditBase;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orden_internamiento_detalle", schema = "gestion")
public class OrdenInternamientoDetalle extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orden_internamiento_id", referencedColumnName = "id")
    private OrdenInternamiento ordenInternamiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;

}
