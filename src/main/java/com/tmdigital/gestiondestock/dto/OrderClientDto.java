package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import com.tmdigital.gestiondestock.model.OrderClient;

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

    public static OrderClientDto fromEntity(OrderClient orderClient) {
        if (orderClient == null) {
            return null;
        }
        return OrderClientDto.builder()
                .id(orderClient.getId())
                .code(orderClient.getCode())
                .dateCommande(orderClient.getDateCommande())
                .idCompany(orderClient.getIdCompany())
                .client(ClientDto.fromEntity(orderClient.getClient()))
                .build();
    }

    public static OrderClient toEntity(OrderClientDto orderClientDto) {
        if (orderClientDto == null) {
            return null;
        }
        OrderClient orderClient = new OrderClient();
        orderClient.setId(orderClientDto.getId());
        orderClient.setCode(orderClientDto.getCode());
        orderClient.setDateCommande(orderClientDto.getDateCommande());
        orderClient.setIdCompany(orderClientDto.getIdCompany());
        orderClient.setClient(ClientDto.toEntity(orderClientDto.getClient()));
        return orderClient;
    }
}
