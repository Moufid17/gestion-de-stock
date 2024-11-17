package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderSupplier;

public interface OrderSupplierRepository extends CrudRepository<OrderSupplier, Integer> {

    OrderSupplier findByCode(String code);

    List<OrderSupplier> findAllBySupplierId(Integer idSupplier);
}