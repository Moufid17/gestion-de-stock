package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.SalesLine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesLineDto {

    private Integer id;

    @JsonIgnore
    private SalesDto sales;

    private ArticleDto article;

    private BigDecimal sellPriceInclTax;
    
    private BigDecimal qty;

    private Integer idCompany;

    public static SalesLineDto fromEntity(SalesLine salesLine) {
        if (salesLine == null) {
            return null;
        }
        return SalesLineDto.builder()
                .id(salesLine.getId())
                .sellPriceInclTax(salesLine.getSellPriceInclTax())
                .qty(salesLine.getQty())
                .idCompany(salesLine.getIdCompany())
                .article(ArticleDto.fromEntity(salesLine.getArticle()))
                .build();
    }

    public static SalesLine toEntity(SalesLineDto salesLineDto) {
        if (salesLineDto == null) {
            return null;
        }
        SalesLine salesLine = new SalesLine();
        salesLine.setId(salesLineDto.getId());
        salesLine.setSellPriceInclTax(salesLineDto.getSellPriceInclTax());
        salesLine.setQty(salesLineDto.getQty());
        salesLine.setIdCompany(salesLineDto.getIdCompany());
        salesLine.setArticle(ArticleDto.toEntity(salesLineDto.getArticle()));
        return salesLine;
    }
}
