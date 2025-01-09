package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.CategoryApi;
import com.tmdigital.gestiondestock.dto.CategoryDto;
import com.tmdigital.gestiondestock.services.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController implements CategoryApi {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        return categoryService.save(dto);
    }

    @Override
    public CategoryDto findById(Integer id) {
        return categoryService.findById(id);
    }

    @Override
    public CategoryDto findByCode(String code) {
        return categoryService.findByCode(code);
    }

    @Override
    public List<CategoryDto> findAllByCompany(Integer idCompany) {
        return categoryService.findAllByCompany(idCompany);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @Override
    public void delete(Integer id) {
        categoryService.delete(id);
    }
}
