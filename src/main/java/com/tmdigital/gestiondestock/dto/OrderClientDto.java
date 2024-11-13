package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class OrderClientDto {

    private Integer id;
    
    private String code;

    private Instant dateCommande;

    private Integer idCompany;

    private ClientDto client;

    private List<OrderLineClientDto> orderLineClients;
}
