package com.tmdigital.gestiondestock.services;

import java.math.BigDecimal;
import java.util.List;

import com.tmdigital.gestiondestock.dto.StockMovementDto;
import com.tmdigital.gestiondestock.model.MovementSource;

public interface StockMovementService {

    BigDecimal realStockArticle(Integer idArticle);

    List<StockMovementDto> stockMovementArticle(Integer id);

    void stockIn(StockMovementDto dto);
    
    void stockOut(StockMovementDto dto);
    
    void updateIn(StockMovementDto dto);
    
    void updateOut(StockMovementDto dto);

    StockMovementDto findById(Integer id);

    StockMovementDto findByOrderIdAndOrderlineId(Integer id, Integer orderlineId);

    StockMovementDto findByOrderIdAndOrderlineIdAndSourceType(Integer orderId, Integer orderlineId, MovementSource sourceType);

    List<StockMovementDto> findAllByTypeMvt(String typeMvt);

    List<StockMovementDto> findAllBysourceMvt(String sourceMvt);

    List<StockMovementDto> findAllByCompanyId(Integer id);
    
    List<StockMovementDto> findAll();

    void delete(Integer id);

    void deleteAllByOrderId(Integer id);

}
