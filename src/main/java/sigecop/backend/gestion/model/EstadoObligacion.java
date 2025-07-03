package sigecop.backend.gestion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "estado_pedido", schema = "gestion")
public class EstadoObligacion extends AuditBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "detalle")
    private String detalle;
    
    @Column(name = "inicial", nullable = false)
    private Boolean inicial;
}
