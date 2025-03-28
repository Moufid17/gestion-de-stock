package com.tmdigital.gestiondestock.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_movement")
public class StockMovement extends AbstractEntity {
    
    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @Column(name = "qty")
    private BigDecimal qty;
    
    @Column(name = "date_movement")
    private Instant dateMovement = Instant.now();

    @Column(name = "typeMvt")
    @Enumerated(EnumType.STRING)
    private StockMovementType typeMvt;

    @Column(name = "sourceMvt")
    @Enumerated(EnumType.STRING)
    private MovementSource sourceMvt;

    @Column(name = "orderId")
    private Integer orderId; // Sales, OrderClient, OrderFournisseur
    
    @Column(name = "orderlineId")
    private Integer orderlineId;

    @Column(name = "idcompany")
    private Integer idCompany;
    
}
