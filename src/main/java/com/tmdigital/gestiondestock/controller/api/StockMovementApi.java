package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tmdigital.gestiondestock.dto.StockMovementDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "StockMovement", description = "The stockMovement API")
public interface StockMovementApi {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    StockMovementDto findById(Integer id);
    
    @GetMapping(value = "/article/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<StockMovementDto> findAllByArticleId(@PathVariable("") Integer id);
    
    @GetMapping(value = "/type={id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<StockMovementDto> findAllByTypeMvt(@PathVariable("id") String typeMvt);
    
    @GetMapping(value = "/source={id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<StockMovementDto> findAllBysourceMvt(@PathVariable("id") String sourceMvt);
    
    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<StockMovementDto> findAllByCompanyId(@PathVariable Integer id);
    
    List<StockMovementDto> findAll();
}
