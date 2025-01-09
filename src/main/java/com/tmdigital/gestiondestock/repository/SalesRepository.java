package com.tmdigital.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Sales;

public interface SalesRepository extends CrudRepository<Sales, Integer> {

    Optional<Sales> findSalesByCode(String code);

    @Override
    List<Sales> findAll();

    List<Sales> findAllByIdCompany(Integer idCompany);
}