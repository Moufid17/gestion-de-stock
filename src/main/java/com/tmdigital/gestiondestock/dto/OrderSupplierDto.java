package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.tmdigital.gestiondestock.model.OrderLineSupplier;
import com.tmdigital.gestiondestock.model.OrderStatus;
import com.tmdigital.gestiondestock.model.OrderSupplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderSupplierDto {

    private Integer id;
    
    private String code;

    @Builder.Default
    private Instant dateCommande = Instant.now();

    private OrderStatus status;

    private Integer idCompany;

    private SupplierDto supplier;

    private List<OrderLineSupplierDto> orderLineSupplier;

    public static OrderSupplierDto fromEntity (OrderSupplier orderSupplier) {
        if (orderSupplier == null) {
            return null;
        }

        List<OrderLineSupplierDto> orderLineSupppliersList = null;
        if (orderSupplier.getOrderLineSupplier() != null && orderSupplier.getOrderLineSupplier().size() > 0) {
            orderLineSupppliersList = orderSupplier.getOrderLineSupplier().stream()
                                .map(OrderLineSupplierDto::fromEntity)
                                .collect(Collectors.toList());
        }

        return OrderSupplierDto.builder()
            .id(orderSupplier.getId())
            .code(orderSupplier.getCode())
            .dateCommande(orderSupplier.getDateCommande())
            .status(orderSupplier.getStatus())
            .idCompany(orderSupplier.getIdCompany())
            .supplier(SupplierDto.fromEntity(orderSupplier.getSupplier()))
            .orderLineSupplier(orderLineSupppliersList)
            .build();
    }

    public static OrderSupplier toEntity (OrderSupplierDto orderSupplierDto) {
        if (orderSupplierDto == null) {
            return null;
        }

        List<OrderLineSupplier> orderLineSupppliersList = null;
        if (orderSupplierDto.getOrderLineSupplier() != null && orderSupplierDto.getOrderLineSupplier().size() > 0) {
            orderLineSupppliersList = orderSupplierDto.getOrderLineSupplier().stream()
                .map(OrderLineSupplierDto::toEntity)
                .collect(Collectors.toList());
        }

        OrderSupplier orderSupplier = new OrderSupplier();
        orderSupplier.setId(orderSupplierDto.getId());
        orderSupplier.setCode(orderSupplierDto.getCode());
        orderSupplier.setDateCommande(orderSupplierDto.getDateCommande());
        orderSupplier.setStatus(orderSupplierDto.getStatus());
        orderSupplier.setIdCompany(orderSupplierDto.getIdCompany());
        orderSupplier.setSupplier(SupplierDto.toEntity(orderSupplierDto.getSupplier()));
        orderSupplier.setOrderLineSupplier(orderLineSupppliersList);
        return orderSupplier;
    }

    public boolean isCancaled() {
        return OrderStatus.CANCELED.equals(this.status);
    }

    public boolean isDelivered() {
        return OrderStatus.DELIVERED.equals(this.status);
    }
}
