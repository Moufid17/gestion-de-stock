package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderClient;

public interface OrderClientRepository extends CrudRepository<OrderClient, Integer> {

    List<OrderClient> findAllByArticleId(Integer idArticle);
    
    List<OrderClient> findAllByClientId(Integer idClient);

}