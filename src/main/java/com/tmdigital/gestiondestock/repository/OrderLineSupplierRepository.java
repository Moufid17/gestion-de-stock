package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.OrderLineSupplier;

public interface OrderLineSupplierRepository extends CrudRepository<OrderLineSupplier, Integer> {

    OrderLineSupplier findByOrderSupplierIdAndArticleId(Integer orderSupplierId, Integer articleId);
    
    List<OrderLineSupplier> findAllByOrderSupplierId(Integer id);
    
    List<OrderLineSupplier> findAllByArticleId(Integer id);


}