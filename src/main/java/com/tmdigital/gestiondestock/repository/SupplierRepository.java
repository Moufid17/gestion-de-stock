package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

}