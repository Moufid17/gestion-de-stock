package com.tmdigital.gestiondestock.services;

import java.math.BigDecimal;
import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.model.OrderStatus;

public interface OrderClientService {

    OrderClientDto save(OrderClientDto dto);

    void updateOrderStatus(Integer orderId, OrderStatus newStatus);

    void updateOrderLineQte(Integer orderId, Integer orderLineId, BigDecimal qte);

    OrderClientDto findById(Integer id);

    OrderClientDto findByCode(String code);

    List<OrderClientDto> findAll();

    List<OrderClientDto> findAllByCompany(Integer id);

    List<OrderClientDto> findAllByClient(Integer id);

    void delete(Integer id);
}
