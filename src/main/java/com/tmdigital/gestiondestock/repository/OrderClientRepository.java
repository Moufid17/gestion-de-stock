package com.tmdigital.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderClient;

public interface OrderClientRepository extends CrudRepository<OrderClient, Integer> {

    Optional<OrderClient> findOrderClientByCode(String code);
    
    List<OrderClient> findAllByClientId(Integer id);

}