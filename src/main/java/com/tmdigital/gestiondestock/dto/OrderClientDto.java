package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.tmdigital.gestiondestock.model.OrderClient;
import com.tmdigital.gestiondestock.model.OrderLineClient;
import com.tmdigital.gestiondestock.model.OrderStatus;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.Builder;

@Data
@Builder
@Slf4j
public class OrderClientDto {

    private Integer id;
    
    private String code;

    private Instant dateCommande;

    private OrderStatus status;

    private Integer idCompany;

    private ClientDto client;

    private List<OrderLineClientDto> orderLineClients;

    public static OrderClientDto fromEntity(OrderClient orderClient) {
        if (orderClient == null) {
            return null;
        }
        
        List<OrderLineClientDto> orderLineClientsList = null;
        if (orderClient.getOrderLineClients() != null && orderClient.getOrderLineClients().size() > 0) {
            orderLineClientsList = orderClient.getOrderLineClients().stream()
                                .map(OrderLineClientDto::fromEntity)
                                .collect(Collectors.toList());
        }

        return OrderClientDto.builder()
                .id(orderClient.getId())
                .code(orderClient.getCode())
                .dateCommande(orderClient.getDateCommande())
                .status(orderClient.getStatus())
                .idCompany(orderClient.getIdCompany())
                .client(ClientDto.fromEntity(orderClient.getClient()))
                .orderLineClients(orderLineClientsList)
                .build();
    }

    public static OrderClient toEntity(OrderClientDto orderClientDto) {
        if (orderClientDto == null) {
            return null;
        }

        List<OrderLineClient> orderLineClientsList = null;
        if (orderClientDto.getOrderLineClients() != null && orderClientDto.getOrderLineClients().size() > 0) {
            orderLineClientsList = orderClientDto.getOrderLineClients().stream()
                .map(OrderLineClientDto::toEntity)
                .collect(Collectors.toList());
        }
        
        OrderClient orderClient = new OrderClient();
        orderClient.setId(orderClientDto.getId());
        orderClient.setCode(orderClientDto.getCode());
        orderClient.setDateCommande(orderClientDto.getDateCommande());
        orderClient.setStatus(orderClientDto.getStatus());
        orderClient.setIdCompany(orderClientDto.getIdCompany());
        orderClient.setClient(ClientDto.toEntity(orderClientDto.getClient()));
        orderClient.setOrderLineClients(orderLineClientsList);
        return orderClient;
    }

    public boolean isCancaled() {
        return OrderStatus.CANCELED.equals(this.status);
    }

    public boolean isDelivered() {
        return OrderStatus.DELIVERED.equals(this.status);
    }
}
