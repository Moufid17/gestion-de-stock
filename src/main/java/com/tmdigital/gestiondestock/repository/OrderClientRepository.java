package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderClient;

public interface OrderClientRepository extends CrudRepository<OrderClient, Integer> {

}