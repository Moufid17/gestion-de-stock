package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLineSupplierDto {
    
    private Integer id;
    
    @Builder.Default
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO; // Prix unitaire de vente TTC.

    @Builder.Default
    private BigDecimal qty = BigDecimal.ZERO;

    private Integer idCompany;

    private ArticleDto article;

    private OrderSupplierDto orderSupplier;
}
