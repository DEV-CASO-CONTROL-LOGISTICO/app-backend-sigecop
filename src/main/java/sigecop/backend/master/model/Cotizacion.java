/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sigecop.backend.master.model;

import sigecop.backend.utils.AuditBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import java.math.BigDecimal;
/**
 *
 * @author jhochuq
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cotizacion", schema = "gestion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private EstadoCotizacion estadoCotizacion;
    @Column(name = "monto",precision = 10, scale = 2)
    private BigDecimal monto;
    @Column(name = "fecha_emision")
    private Date fechaEmision;
    @Column(name = "comentario")
    private String comentario;
    
    
}
