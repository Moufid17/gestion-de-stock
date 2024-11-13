package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import com.tmdigital.gestiondestock.model.Article;

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

    public static ArticleDto fromEntity(Article article) {
        if (article == null) return null;

        return ArticleDto.builder()
            .id(article.getId())
            .code(article.getCode())
            .designation(article.getDesignation())
            .buyPrice(article.getBuyPrice())
            .sellPrice(article.getSellPrice())
            .vatRates(article.getVatRates())
            .sellPriceInclTax(article.getSellPriceInclTax())
            .photo(article.getPhoto())
            .alertStock(article.getAlertStock())
            .company(article.getCompany())
            .category(CategoryDto.fromEntity(article.getCategory())) // Imprtant to use fromEntity() method
            .build();
    }

    public static Article toEntity(ArticleDto articleDto) {
        if (articleDto == null) return null;

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCode(articleDto.getCode());
        article.setDesignation(articleDto.getDesignation());
        article.setBuyPrice(articleDto.getBuyPrice());
        article.setSellPrice(articleDto.getSellPrice());
        article.setVatRates(articleDto.getVatRates());
        article.setSellPriceInclTax(articleDto.getSellPriceInclTax());
        article.setPhoto(articleDto.getPhoto());
        article.setAlertStock(articleDto.getAlertStock());
        article.setCompany(articleDto.getCompany());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory())); // Imprtant to use toEntity() method

        return article;
    }

}
