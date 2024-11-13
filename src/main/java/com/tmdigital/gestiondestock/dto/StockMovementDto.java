package com.tmdigital.gestiondestock.dto;

import java.math.BigDecimal;
import java.time.Instant;

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

    private StockMovementType type;

}
