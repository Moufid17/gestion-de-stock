package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.ArticleDto;


public interface ArticleApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> getAll();

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto get(@PathVariable Integer id);
    
    @DeleteMapping(value="/{id}")
    void delete(@PathVariable Integer id);
}
