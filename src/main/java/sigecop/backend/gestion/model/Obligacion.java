package sigecop.backend.gestion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.TipoObligacion;
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
@Table(name = "obligacion", schema = "gestion")
public class Obligacion extends AuditBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "codigo")
    private String codigo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_id", referencedColumnName = "id")
    private TipoObligacion tipo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private EstadoObligacion estado;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_creacion_id", referencedColumnName = "id")
    private Usuario usuarioCreacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_estado_id", referencedColumnName = "id")
    private Usuario usuarioEstado;   
    
    @Column(name = "cuenta_bancaria_temporal")
    private String cuentaBancariaTemporal; 
    
    @Column(name = "nombre_usuario_pago")
    private String nombreUsuarioPago;
    
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    
    @Column(name = "fecha_pago")
    private Date fechaPago;
    
    @Column(name = "comentario")
    private String comentario;
    
}
