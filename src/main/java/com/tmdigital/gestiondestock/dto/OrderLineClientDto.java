package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLineClientDto {

    private Integer id;
    
    @Builder.Default
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal qty = BigDecimal.ZERO;

    private Integer idCompany;

    private ArticleDto article;

    private OrderClientDto orderClient;
}
