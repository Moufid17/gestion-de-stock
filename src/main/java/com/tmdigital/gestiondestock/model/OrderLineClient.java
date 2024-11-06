package com.tmdigital.gestiondestock.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data // Génère les méthodes getter, setter, toString, equals, et hashCode
@EqualsAndHashCode(callSuper = true) // Inclut les champs de la classe parente dans equals et hashCode
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Entity
@Table(name = "order_line_client")
public class OrderLineClient extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;
    
    @Column(name = "qty")
    private BigDecimal qty = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "id_order_client")
    private OrderClient orderClient;

}