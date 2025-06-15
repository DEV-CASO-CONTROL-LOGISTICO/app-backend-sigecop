
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
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.Proveedor;
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
public class Pedido extends AuditBase{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "codigo")
    private String codigo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private EstadoPedido estado;
    
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "observacion")
    private String observacion; 
    
    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal montoTotal;
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_creacion_id", referencedColumnName = "id")
    private Usuario usuarioCreacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_estado_id", referencedColumnName = "id")
    private Usuario usuarioEstado;
        
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
        
    @Column(name = "numero_factura")
    private String numeroFactura;
    
    @Column(name = "serie_guia")
    private String serieGuia;
    
    @Column(name = "numero_guia")
    private String numeroGuia;
    
    @Column(name = "fecha_estimada_entrega")
    private Date fechaEntrega;
    
    @Column(name = "observacion_envio")
    private String observacionEnvio;

}
