package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.StockMovement;

public interface StockMovementRepository extends CrudRepository<StockMovement, Integer> {

}