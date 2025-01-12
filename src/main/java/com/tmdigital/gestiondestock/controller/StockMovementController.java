package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.StockMovementApi;
import com.tmdigital.gestiondestock.dto.StockMovementDto;
import com.tmdigital.gestiondestock.services.impl.StockMovementServiceImpl;

@RestController
@RequestMapping("/api/v1/stockmovements")
public class StockMovementController implements StockMovementApi {

    private StockMovementServiceImpl stockMovementService;

    public StockMovementController(StockMovementServiceImpl stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @Override
    public StockMovementDto findById(Integer id) {
        return stockMovementService.findById(id);
    }

    @Override
    public List<StockMovementDto> findAllByArticleId(Integer id) {
        return stockMovementService.findAllByArticleId(id);
    }

    @Override
    public List<StockMovementDto> findAllByTypeMvt(String typeMvt) {
        return stockMovementService.findAllByTypeMvt(typeMvt);
    }

    @Override
    public List<StockMovementDto> findAllBysourceMvt(String sourceMvt) {
        return stockMovementService.findAllBysourceMvt(sourceMvt);
    }

    @Override
    public List<StockMovementDto> findAllByCompanyId(Integer id) {
        return stockMovementService.findAllByCompanyId(id);
    }

    @Override
    public List<StockMovementDto> findAll() {
        return stockMovementService.findAll();
    }

}
