package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ArticleDto {

    private Integer id;
    
    private String code;
    
    private String designation;
    
    @Builder.Default
    private BigDecimal buyPrice = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal sellPrice = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal vatRates = BigDecimal.ZERO;
    
    @Builder.Default
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO;
    
    private String photo;

    @Builder.Default
    private Integer alertStock = 0;

    private CategoryDto category;

    private Integer company;

}
