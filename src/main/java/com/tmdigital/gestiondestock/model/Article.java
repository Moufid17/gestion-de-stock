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

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "article")
public class Article  extends AbstractEntity {

    @Column(name = "code")
    private String code;
    
    @Column(name = "designation")
    private String designation;

    @Column(name = "buy_price")
    private BigDecimal buyPrice = BigDecimal.ZERO;

    @Column(name = "sell_price")
    private BigDecimal sellPrice = BigDecimal.ZERO;

    @Column(name = "vat_rates")
    private BigDecimal vatRates = BigDecimal.ZERO;

    @Column(name = "sell_price_tax")
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO;
    
    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @Column(name = "id_company")
    private Integer company;

    @OneToMany(mappedBy = "article")
    private List<SalesLine> salesLine;

    @OneToMany(mappedBy = "article")
    private List<OrderLineClient> orderLineClient;

    @OneToMany(mappedBy = "article")
    private List<OrderLineSupplier> orderLineSupplier;

    @OneToMany(mappedBy = "article")
    private List<StockMovement> stockMovement;
}
