package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

    @Override
    List<Supplier> findAll();
    
    List<Supplier> findAllByIdCompany(Integer id);

}