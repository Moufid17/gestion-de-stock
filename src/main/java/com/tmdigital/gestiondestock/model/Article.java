package com.tmdigital.gestiondestock.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "article")
public class Article  extends AbstractEntity {
    private String code;
    private String designation;
    
    @Column(name = "buy_price")
    private BigDecimal buyPrice = BigDecimal.ZERO;

    @Column(name = "sell_price")
    private BigDecimal sellPrice = BigDecimal.ZERO;

    @Column(name = "vat_rates")
    private BigDecimal vatRates = BigDecimal.ZERO;
    
    @Column(name = "sell_price_tax")
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO;
    
    private String photo;
    @Column(name = "alert_stock", nullable = false)
    private Integer alertStock = 0;

    @ManyToOne
    @JoinColumn(name = "id_category") // The name of the column in the table that owns the relationship.
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<OrderLineClient> orderLineClient;

    @OneToMany(mappedBy = "article")
    private List<OrderLineSupplier> orderLineSupplier;

    @OneToMany(mappedBy = "article")
    private List<SalesLine> salesLine;

}
