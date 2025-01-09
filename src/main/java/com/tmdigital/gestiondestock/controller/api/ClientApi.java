package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.ClientDto;

public interface ClientApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ClientDto save(@RequestBody ClientDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findById(@PathVariable Integer id);

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAllByCompany(@PathVariable Integer id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAll();

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id);

}