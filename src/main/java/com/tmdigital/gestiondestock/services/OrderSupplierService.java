package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;

public interface OrderSupplierService {

    OrderSupplierDto save(OrderSupplierDto dto);

    OrderSupplierDto addClientOrderLine(Integer orderId, OrderLineSupplierDto dto);

    OrderSupplierDto findById(Integer id);

    OrderSupplierDto findByCode(String code);

    List<OrderLineSupplierDto> findAllOrderLine(Integer orderId);

    List<OrderSupplierDto> findAll();

    List<OrderSupplierDto> findAllByCompany(Integer id);

    List<OrderSupplierDto> findAllBySupplier(Integer id);

    void delete(Integer id);
}
