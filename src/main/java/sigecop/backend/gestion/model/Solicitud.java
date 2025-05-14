
package sigecop.backend.gestion.model;

/**
 *
 * @author Diego Poma
 */

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

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitud", schema = "gestion")
public class Solicitud extends AuditBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "descripcion")
    private String descripcion;    
    
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    
    @Column(name = "fecha_finalizado")
    private Date fechaFinalizado;
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_creacion_id", referencedColumnName = "id")
    private Usuario usuarioCreacion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_estado_id", referencedColumnName = "id")
    private Usuario usuarioEstado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private EstadoSolicitud estado;
}
