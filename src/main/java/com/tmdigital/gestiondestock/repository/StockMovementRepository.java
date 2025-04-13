package com.tmdigital.gestiondestock.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tmdigital.gestiondestock.model.MovementSource;
import com.tmdigital.gestiondestock.model.StockMovement;

public interface StockMovementRepository extends CrudRepository<StockMovement, Integer> {

    @Query("SELECT SUM(m.qty) FROM StockMovement m WHERE m.article.id = :articleId AND m.orderStatus = 'DELIVERED'")
    BigDecimal stockReel(@Param("articleId") Integer articleId);

    List<StockMovement> findAllByArticleId(Integer articleId);

    @Query("SELECT m FROM StockMovement m WHERE m.article.id = :articleId AND m.orderStatus = 'DELIVERED'")
    List<StockMovement> findAllDeliveredByArticleId(@Param("articleId") Integer articleId);

    @Query("SELECT m FROM StockMovement m WHERE m.orderId = :orderId AND m.orderlineId = :orderlineId")
    Optional<StockMovement> findByOrderIdAndOrderlineId(@Param("orderId") Integer orderId, @Param("orderlineId") Integer orderlineId);
    
    @Query("SELECT m FROM StockMovement m WHERE m.orderId = :orderId AND m.orderlineId = :orderlineId AND m.sourceMvt = :sourceType")
    Optional<StockMovement> findByOrderIdAndOrderlineIdAndSourceType(@Param("orderId") Integer orderId, @Param("orderlineId") Integer orderlineId, @Param("sourceType") MovementSource sourceType);

    List<StockMovement> findAllByTypeMvt(String typeMvt);

    List<StockMovement> findAllBysourceMvt(String sourceMvt);

    @Query("SELECT m FROM StockMovement m WHERE m.companyId = :companyId")
    List<StockMovement> findAllByCompanyId(@Param("companyId")Integer companyId);
    
    List<StockMovement> findAll();

    @Query("DELETE FROM StockMovement m WHERE m.orderId = :orderId")
    void deleteAllByOrderId(@Param("orderId") Integer orderId);
}