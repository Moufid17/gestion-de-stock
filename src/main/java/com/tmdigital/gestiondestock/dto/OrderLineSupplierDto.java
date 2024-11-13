package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.OrderLineSupplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLineSupplierDto {
    
    private Integer id;
    
    @Builder.Default
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal qty = BigDecimal.ZERO;

    private Integer idCompany;

    private ArticleDto article;

    @JsonIgnore
    private OrderSupplierDto orderSupplier;

    public static OrderLineSupplierDto fromEntity (OrderLineSupplier orderLineSupplier) {
        if (orderLineSupplier == null) {
            return null;
        }
        return OrderLineSupplierDto.builder()
            .id(orderLineSupplier.getId())
            .sellPriceInclTax(orderLineSupplier.getSellPriceInclTax())
            .qty(orderLineSupplier.getQty())
            .idCompany(orderLineSupplier.getIdCompany())
            .article(ArticleDto.fromEntity(orderLineSupplier.getArticle()))
            .build();
    }

    public static OrderLineSupplier toEntity (OrderLineSupplierDto orderLineSupplierDto) {
        if (orderLineSupplierDto == null) {
            return null;
        }
        OrderLineSupplier orderLineSupplier = new OrderLineSupplier();
        orderLineSupplier.setId(orderLineSupplierDto.getId());
        orderLineSupplier.setSellPriceInclTax(orderLineSupplierDto.getSellPriceInclTax());
        orderLineSupplier.setQty(orderLineSupplierDto.getQty());
        orderLineSupplier.setIdCompany(orderLineSupplierDto.getIdCompany());
        orderLineSupplier.setArticle(ArticleDto.toEntity(orderLineSupplierDto.getArticle()));
        return orderLineSupplier;
    }
}
