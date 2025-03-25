package com.tmdigital.gestiondestock.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tmdigital.gestiondestock.model.StockMovement;

public interface StockMovementRepository extends CrudRepository<StockMovement, Integer> {

    @Query("SELECT SUM(m.qty) FROM StockMovement m WHERE m.article.id = :idArticle")
    BigDecimal stockReel(@Param("idArticle") Integer idArticle);

    List<StockMovement> findAllByArticleId(Integer idArticle);

    @Query("SELECT m FROM StockMovement m WHERE m.orderId = :orderId AND m.article.id = :idArticle")
    Optional<StockMovement> findByOrderIdAndArticleId(@Param("orderId") Integer orderId,@Param("idArticle") Integer idArticle);

    List<StockMovement> findAllByTypeMvt(String typeMvt);

    List<StockMovement> findAllBysourceMvt(String sourceMvt);

    @Query("SELECT m FROM StockMovement m WHERE m.idCompany = :idCompany")
    List<StockMovement> findAllByIdCompany(Integer idCompany);
    
    List<StockMovement> findAll();

    @Query("DELETE FROM StockMovement m WHERE m.orderId = :orderId")
    void deleteAllByOrderId(@Param("orderId") Integer orderId);
}