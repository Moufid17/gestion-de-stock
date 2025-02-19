package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

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
        return OrderSupplierDto.builder()
            .id(orderSupplier.getId())
            .code(orderSupplier.getCode())
            .dateCommande(orderSupplier.getDateCommande())
            .status(orderSupplier.getStatus())
            .idCompany(orderSupplier.getIdCompany())
            .supplier(SupplierDto.fromEntity(orderSupplier.getSupplier()))
            .build();
    }

    public static OrderSupplier toEntity (OrderSupplierDto orderSupplierDto) {
        if (orderSupplierDto == null) {
            return null;
        }
        OrderSupplier orderSupplier = new OrderSupplier();
        orderSupplier.setId(orderSupplierDto.getId());
        orderSupplier.setCode(orderSupplierDto.getCode());
        orderSupplier.setDateCommande(orderSupplierDto.getDateCommande());
        orderSupplier.setStatus(orderSupplierDto.getStatus());
        orderSupplier.setIdCompany(orderSupplierDto.getIdCompany());
        orderSupplier.setSupplier(SupplierDto.toEntity(orderSupplierDto.getSupplier()));
        return orderSupplier;
    }

    public boolean isCancaled() {
        return OrderStatus.CANCELED.equals(this.status);
    }

    public boolean isDelivered() {
        return OrderStatus.DELIVERED.equals(this.status);
    }
}
