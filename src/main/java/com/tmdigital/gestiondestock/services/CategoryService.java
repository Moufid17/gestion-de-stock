package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.CategoryDto;

public interface CategoryService {

    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer id);

    CategoryDto findByCode(String code);

    List<CategoryDto> findAll();
    
    List<CategoryDto> findAllByCompany(Integer idCompany);
    
    void delete(Integer id);
}
