package com.tmdigital.gestiondestock.services;

import java.math.BigDecimal;
import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.dto.OrderLineClientDto;
import com.tmdigital.gestiondestock.model.OrderStatus;

public interface OrderClientService {

    OrderClientDto save(OrderClientDto dto);

    OrderClientDto addClientOrderLine(Integer orderId, OrderLineClientDto dto);

    void updateOrderStatus(Integer orderId, OrderStatus newStatus);

    void updateOrderLineQte(Integer orderId, Integer orderLineId, BigDecimal qte);

    void updateClient(Integer orderId, Integer clientId);

    void updateArticle(Integer orderId, Integer orderLineId, Integer newArticleId);

    OrderClientDto findById(Integer id);

    OrderClientDto findByCode(String code);

    List<OrderClientDto> findAll();

    List<OrderLineClientDto> findAllOrderLine(Integer orderId);

    List<OrderClientDto> findAllByCompany(Integer id);

    List<OrderClientDto> findAllByClient(Integer id);

    void delete(Integer id);

    void deleteOrderLine(Integer id, Integer orderLineId);
}
