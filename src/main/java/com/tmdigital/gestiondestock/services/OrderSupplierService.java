package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;

public interface OrderSupplierService {

    OrderSupplierDto save(OrderSupplierDto dto);

    OrderSupplierDto findById(Integer id);

    OrderSupplierDto findByCode(String code);

    List<OrderSupplierDto> findAll();

    List<OrderSupplierDto> findAllByCompany(Integer id);

    List<OrderSupplierDto> findAllBySupplier(Integer id);

    void delete(Integer id);
}
