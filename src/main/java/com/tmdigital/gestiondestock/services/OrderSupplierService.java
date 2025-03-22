package com.tmdigital.gestiondestock.services;

import java.math.BigDecimal;
import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;
import com.tmdigital.gestiondestock.model.OrderStatus;

public interface OrderSupplierService {

    OrderSupplierDto save(OrderSupplierDto dto);

    OrderSupplierDto addSupplierOrderLine(Integer orderId, OrderLineSupplierDto dto);

    OrderSupplierDto findById(Integer id);

    OrderSupplierDto findByCode(String code);

    List<OrderLineSupplierDto> findAllOrderLine(Integer orderId);

    List<OrderSupplierDto> findAll();

    List<OrderSupplierDto> findAllByCompany(Integer id);

    List<OrderSupplierDto> findAllBySupplier(Integer id);

    void updateOrderStatus(Integer orderId, OrderStatus newStatus);

    void updateOrderLineQte(Integer orderId, Integer orderLineId, BigDecimal qte);

    void updateSupplier(Integer orderId, Integer supplierId);

    void delete(Integer id);
}
