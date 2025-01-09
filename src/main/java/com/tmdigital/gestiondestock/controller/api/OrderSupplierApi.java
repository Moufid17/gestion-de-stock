package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;

public interface OrderSupplierApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)  
    OrderSupplierDto save(@RequestBody OrderSupplierDto dto);

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderSupplierDto findById(@PathVariable Integer id);

    @GetMapping(value="/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderSupplierDto findByCode(@PathVariable String code);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderSupplierDto> findAll();

    @GetMapping(value="/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderSupplierDto> findAllByCompany(@PathVariable Integer id);

    @GetMapping(value="/supplier/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OrderSupplierDto> findAllBySupplier(@PathVariable Integer id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id);
}
