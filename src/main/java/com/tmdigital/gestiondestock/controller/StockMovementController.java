package com.tmdigital.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.StockMovementApi;
import com.tmdigital.gestiondestock.dto.StockMovementDto;
import com.tmdigital.gestiondestock.services.StockMovementService;

@RestController
@RequestMapping("/api/v1/stockmovements")
public class StockMovementController implements StockMovementApi {

    private StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @Override
    public ResponseEntity<BigDecimal> findRealStock(Integer articleId) {
        return ResponseEntity.ok(stockMovementService.realStockArticle(articleId));
    }

    @Override
    public ResponseEntity<Void> saveStockIn(StockMovementDto dto) {
        stockMovementService.stockIn(dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> saveStockOut(StockMovementDto dto) {
        stockMovementService.stockOut(dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateStockIn(StockMovementDto dto) {
        stockMovementService.updateIn(dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateStockOut(StockMovementDto dto) {
        stockMovementService.updateOut(dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public StockMovementDto findById(Integer id) {
        return stockMovementService.findById(id);
    }

    @Override
    public ResponseEntity<List<StockMovementDto>> findAllByArticleId(Integer articleId) {
        return ResponseEntity.ok(stockMovementService.stockMovementArticle(articleId));
    }

    @Override
    public ResponseEntity<List<StockMovementDto>> findAllByTypeMvt(String typeMvt) {
        return ResponseEntity.ok(stockMovementService.findAllByTypeMvt(typeMvt));
    }

    @Override
    public ResponseEntity<List<StockMovementDto>> findAllBysourceMvt(String sourceMvt) {
        return ResponseEntity.ok(stockMovementService.findAllBysourceMvt(sourceMvt));
    }

    @Override
    public ResponseEntity<List<StockMovementDto>> findAllByCompanyId(Integer id) {
        return ResponseEntity.ok(stockMovementService.findAllByCompanyId(id));
    }

    @Override
    public ResponseEntity<List<StockMovementDto>> findAll() {
        return ResponseEntity.ok(stockMovementService.findAll());
    }

}
