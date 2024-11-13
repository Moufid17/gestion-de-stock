package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderSupplierDto {

    private Integer id;
    
    private String code;

    @Builder.Default
    private Instant dateCommande = Instant.now();

    private Integer idCompany;

    private SupplierDto supplier;

    private List<OrderLineSupplierDto> orderLineSupplier;
}
