package com.tmdigital.gestiondestock.controller.api;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.StockMovementDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "StockMovement", description = "The stockMovement API")
public interface StockMovementApi {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    StockMovementDto findById(Integer id);

    @PostMapping(value = "/stockin", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> saveStockIn(@RequestBody StockMovementDto dto);

    @PostMapping(value = "/stockout", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> saveStockOut(@RequestBody StockMovementDto dto);

    @PatchMapping(value = "/stockin", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateStockIn(@RequestBody StockMovementDto dto);

    @PatchMapping(value = "/stockout", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateStockOut(@RequestBody StockMovementDto dto);

    @GetMapping(value = "/stock/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BigDecimal> findRealStock(@PathVariable Integer articleId);
    
    @GetMapping(value = "/article/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<StockMovementDto>> findAllByArticleId(@PathVariable Integer articleId);
    
    @GetMapping(value = "/type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<StockMovementDto>> findAllByTypeMvt(@PathVariable("id") String typeMvt);
    
    @GetMapping(value = "/source/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<StockMovementDto>> findAllBysourceMvt(@PathVariable("id") String sourceMvt);
    
    @GetMapping(value = "/company/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<StockMovementDto>> findAllByCompanyId(@PathVariable Integer companyId);
    
    ResponseEntity<List<StockMovementDto>> findAll();
}
