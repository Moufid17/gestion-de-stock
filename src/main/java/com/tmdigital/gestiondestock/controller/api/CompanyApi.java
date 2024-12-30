package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.CompanyDto;

public interface CompanyApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    CompanyDto save(@RequestBody CompanyDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDto findById(Integer id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompanyDto> findAll();

    @DeleteMapping("/{id}")
    void delete(Integer id);
}
