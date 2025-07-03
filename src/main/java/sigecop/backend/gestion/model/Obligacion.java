package sigecop.backend.gestion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.security.model.Usuario;
import sigecop.backend.utils.AuditBase;

/**
 *
 * @author Diego Poma
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido", schema = "gestion")
public class Obligacion extends AuditBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;
    
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_registro_id", referencedColumnName = "id")
    private Usuario usuarioRegistro;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private EstadoObligacion estado;
}
