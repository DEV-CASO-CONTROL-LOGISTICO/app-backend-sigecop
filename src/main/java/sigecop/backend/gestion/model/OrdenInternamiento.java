package sigecop.backend.gestion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.master.model.TipoInternamiento;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.utils.AuditBase;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orden_internamiento", schema = "gestion")
public class OrdenInternamiento extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo")
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_id", referencedColumnName = "id")
    private TipoInternamiento tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_creacion_id", referencedColumnName = "id")
    private Usuario usuarioCreacion;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

}
