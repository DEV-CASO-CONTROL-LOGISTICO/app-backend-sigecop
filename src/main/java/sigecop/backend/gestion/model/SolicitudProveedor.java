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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sigecop.backend.master.model.Proveedor;
import sigecop.backend.utils.AuditBase;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitud_proveedor", schema = "gestion")
public class SolicitudProveedor extends AuditBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;   
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id")
    private Solicitud solicitud;
    
    
}
