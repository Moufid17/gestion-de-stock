package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.model.OrderStatus;

public interface OrderClientService {

    OrderClientDto save(OrderClientDto dto);

    OrderClientDto updateOrderStatus(Integer orderId, OrderStatus newStatus);

    OrderClientDto findById(Integer id);

    OrderClientDto findByCode(String code);

    List<OrderClientDto> findAll();

    List<OrderClientDto> findAllByCompany(Integer id);

    List<OrderClientDto> findAllByClient(Integer id);

    void delete(Integer id);
}
