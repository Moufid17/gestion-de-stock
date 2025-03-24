package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.tmdigital.gestiondestock.model.MovementSource;
import com.tmdigital.gestiondestock.model.StockMovement;
import com.tmdigital.gestiondestock.model.StockMovementType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockMovementDto {

    private Integer id;
    
    private ArticleDto article;

    private BigDecimal qty;
    
    private Instant dateMovement;

    private StockMovementType typeMvt;

    private MovementSource sourceMvt;

    private Integer orderId;

    private Integer companyId;

    public static StockMovementDto fromEntity(StockMovement stockMovement) {
        if (stockMovement == null) {
            return null;
        }
        return StockMovementDto.builder()
                .id(stockMovement.getId())
                .article(ArticleDto.fromEntity(stockMovement.getArticle()))
                .qty(stockMovement.getQty())
                .dateMovement(stockMovement.getDateMovement())
                .typeMvt(stockMovement.getTypeMvt())
                .sourceMvt(stockMovement.getSourceMvt())
                .orderId(stockMovement.getOrderId())
                .companyId(stockMovement.getIdCompany())
                .build();
    }

    public static StockMovement toEntity(StockMovementDto stockMovementDto) {
        if (stockMovementDto == null) {
            return null;
        }
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(stockMovementDto.getId());
        stockMovement.setArticle(ArticleDto.toEntity(stockMovementDto.getArticle()));
        stockMovement.setQty(stockMovementDto.getQty());
        stockMovement.setDateMovement(stockMovementDto.getDateMovement());
        stockMovement.setTypeMvt(stockMovementDto.getTypeMvt());
        stockMovement.setSourceMvt(stockMovementDto.getSourceMvt());
        stockMovement.setOrderId(stockMovementDto.getOrderId());
        stockMovement.setIdCompany(stockMovementDto.getCompanyId());
        return stockMovement;
    }

}
