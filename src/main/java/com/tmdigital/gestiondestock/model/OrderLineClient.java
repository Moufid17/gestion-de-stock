package com.tmdigital.gestiondestock.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "order_line_client")
public class OrderLineClient extends AbstractEntity {

    // Le prix d'achat unitaire ttc pour la commande peut être différente du prix d'achat unitaire ttc actuelle de l'article. 
    @Column(name = "sell_price_tax")
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO;

    @Column(name = "qty")
    private BigDecimal qty = BigDecimal.ZERO;

    @Column(name = "idcompany")
    private Integer idCompany;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_article")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "id_order_client")
    private OrderClient orderClient;
}