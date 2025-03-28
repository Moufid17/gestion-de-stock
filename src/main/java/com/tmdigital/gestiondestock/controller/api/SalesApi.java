package com.tmdigital.gestiondestock.controller.api;


import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.SalesDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Sales", description = "The sales API")
public interface SalesApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    SalesDto save(@RequestBody SalesDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    SalesDto findById(@PathVariable Integer id);

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    SalesDto findByCode(@PathVariable String code);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<SalesDto> findAll();

    @GetMapping(value="/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<SalesDto> findAllByCompanyId(@PathVariable Integer id);

    @DeleteMapping(value = "/{id}")
    void delete(@PathVariable Integer id);

}
