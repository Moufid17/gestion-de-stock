package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesLineDto {

    private Integer id;

    private SalesDto sales;

    private ArticleDto article;

    private BigDecimal sellPriceInclTax;
    
    private BigDecimal qty;

    private Integer idCompany;
}
