package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderLineClient;

public interface OrderLineClientRepository extends CrudRepository<OrderLineClient, Integer> {

    List<OrderLineClient> findAllByOrderClientId(Integer idOrderClient);
    
    List<OrderLineClient> findAllByArticleId(Integer idOrderClient);
}