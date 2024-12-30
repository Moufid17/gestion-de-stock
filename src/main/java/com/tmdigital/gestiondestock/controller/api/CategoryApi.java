package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tmdigital.gestiondestock.dto.CategoryDto;


public interface CategoryApi {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto save(CategoryDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE) 
    CategoryDto findById(@PathVariable Integer id);

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto findByCode(@PathVariable String code);

    @GetMapping(value = "/c/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryDto> findAllByCompany(@PathVariable("id") Integer idCompany);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<CategoryDto> findAll();

    @DeleteMapping(value = "/{id}")
    void delete(@PathVariable Integer id);
}
