package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.OrderClientDto;

public interface OrderClientApi {

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)  
    OrderClientDto save(@RequestBody OrderClientDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderClientDto findById(@PathVariable Integer id);
    
    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderClientDto findByCode(@PathVariable String code);

    @GetMapping(value = "/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderClientDto> findAllByClientId(@PathVariable Integer id);

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderClientDto> findAllByCompany(@PathVariable Integer id);
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderClientDto> findAll();

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id);
}
