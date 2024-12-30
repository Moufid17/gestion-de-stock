package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.SupplierDto;

public interface SupplierApi {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SupplierDto save(@RequestBody SupplierDto dto);    

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE) 
    SupplierDto findById(@PathVariable Integer id);

    List<SupplierDto> findAll();

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE) 
    List<SupplierDto> findAllByCompany(@PathVariable Integer id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id);
}
