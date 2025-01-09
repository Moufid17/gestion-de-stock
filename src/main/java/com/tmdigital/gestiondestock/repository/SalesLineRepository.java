package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.SalesLine;

public interface SalesLineRepository extends CrudRepository<SalesLine, Integer> {

    List<SalesLine> findAllBySalesId(Integer idSales);
    
    List<SalesLine> findAllByArticleId(Integer idArticle);
}