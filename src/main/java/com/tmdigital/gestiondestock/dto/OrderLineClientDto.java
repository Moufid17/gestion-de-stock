package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.OrderLineClient;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class OrderLineClientDto {

    private Integer id;
    
    @Builder.Default
    private BigDecimal sellPriceInclTax = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal qty = BigDecimal.ZERO;

    private Integer idCompany;

    private ArticleDto article;

    // @JsonIgnore
    private OrderClientDto orderClient;

    public static OrderLineClientDto fromEntity(OrderLineClient orderLineClient) {
        if (orderLineClient == null) {
            return null;
        }
        
        OrderClientDto orderClientDto = null;
        if (orderLineClient.getOrderClient() != null) {
            orderClientDto = OrderClientDto.fromEntity(orderLineClient.getOrderClient());
        } else {
            log.warn("(OrderClientDto) is null");
        }

        return OrderLineClientDto.builder()
                .id(orderLineClient.getId())
                .sellPriceInclTax(orderLineClient.getSellPriceInclTax())
                .qty(orderLineClient.getQty())
                .idCompany(orderLineClient.getIdCompany())
                .article(ArticleDto.fromEntity(orderLineClient.getArticle()))
                .orderClient(orderClientDto)
                .build();
    }

    public static OrderLineClient toEntity(OrderLineClientDto orderLineClientDto) {
        if (orderLineClientDto == null) {
            return null;
        }
        OrderLineClient orderLineClient = new OrderLineClient();
        orderLineClient.setId(orderLineClientDto.getId());
        orderLineClient.setSellPriceInclTax(orderLineClientDto.getSellPriceInclTax());
        orderLineClient.setQty(orderLineClientDto.getQty());
        orderLineClient.setIdCompany(orderLineClientDto.getIdCompany());
        orderLineClient.setArticle(ArticleDto.toEntity(orderLineClientDto.getArticle()));
        orderLineClient.setOrderClient(OrderClientDto.toEntity(orderLineClientDto.getOrderClient()));
        return orderLineClient;
    } 
}
