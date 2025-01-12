package com.tmdigital.gestiondestock.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tmdigital.gestiondestock.model.StockMovement;

public interface StockMovementRepository extends CrudRepository<StockMovement, Integer> {

    @Query("SELECT SUM(m.qty) FROM StockMovement m WHERE m.article.id = :idArticle")
    BigDecimal stockReel(@Param("idArticle") Integer idArticle);

    List<StockMovement> findAllByArticleId(Integer idArticle);

    List<StockMovement> findAllByTypeMvt(String typeMvt);

    List<StockMovement> findAllBysourceMvt(String sourceMvt);

    @Query("SELECT m FROM StockMovement m WHERE m.idCompany = :idCompany")
    List<StockMovement> findAllByIdCompany(Integer idCompany);
    
    @Query("SELECT m FROM StockMovement m")
    List<StockMovement> findAll();
}