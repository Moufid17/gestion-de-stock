package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderLineClient;

public interface OrderLineClientRepository extends CrudRepository<OrderLineClient, Integer> {

}