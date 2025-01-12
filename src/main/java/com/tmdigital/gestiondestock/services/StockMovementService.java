package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.StockMovementDto;

public interface StockMovementService {


    StockMovementDto findById(Integer id);

    List<StockMovementDto> findAllByArticleId(Integer id);
    
    List<StockMovementDto> findAllByTypeMvt(String typeMvt);

    List<StockMovementDto> findAllBysourceMvt(String sourceMvt);

    List<StockMovementDto> findAllByCompanyId(Integer id);
    
    List<StockMovementDto> findAll();

}
