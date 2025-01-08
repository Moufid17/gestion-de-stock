package com.tmdigital.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderSupplier;

public interface OrderSupplierRepository extends CrudRepository<OrderSupplier, Integer> {

    Optional<OrderSupplier> findByCode(String code);

    @Override
    List<OrderSupplier> findAll();

    List<OrderSupplier> findAllByIdCompany(Integer id);

    List<OrderSupplier> findAllBySupplierId(Integer id);
}