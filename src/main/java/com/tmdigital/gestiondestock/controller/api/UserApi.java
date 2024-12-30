package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tmdigital.gestiondestock.dto.UserDto;

public interface UserApi {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto save(UserDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto findById(@PathVariable Integer id);

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto findByEmail(@PathVariable String email);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> findAll();

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> findAllByCompany(@PathVariable("id") Integer idCompany);

    @DeleteMapping(value = "/{id}")
    void delete(@PathVariable Integer id);
}
