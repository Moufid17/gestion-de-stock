package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tmdigital.gestiondestock.dto.CategoryDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.repository.CategoryRepository;
import com.tmdigital.gestiondestock.services.CategoryService;
import com.tmdigital.gestiondestock.validator.CategoryValidator;

public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Category n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }

        return CategoryDto.fromEntity(
                categoryRepository.save(
                    CategoryDto.toEntity(dto)
                )
            );
    }

    @Override
    public CategoryDto findById(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("L'id de la category est nulle");
        }

        return Optional.of(CategoryDto.fromEntity(
            categoryRepository.findById(id).get()
        )).orElseThrow(
            () -> new InvalidEntityException("La category avec l'id = " + id + ", n'existe pas.", ErrorCodes.CATEGORY_NOT_FOUND)
        );
    }

    @Override
    public CategoryDto findByCode(String code) {
        if (code == null) {
            throw new InvalidEntityException("Le code de la category est nulle");
        }

        return Optional.of(CategoryDto.fromEntity(
            categoryRepository.findCategoryByCode(code).get()
        )).orElseThrow(
            () -> new InvalidEntityException("La category avec le code = " + code + ", n'existe pas.", ErrorCodes.CATEGORY_NOT_FOUND)
        );
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
            .map(CategoryDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findAllByCompany(Integer idCompany) {
        if (idCompany == null) {
            throw new InvalidEntityException("L'id de l'entrprise est nulle");
        }

        return categoryRepository.findAllByCompany(idCompany).stream()
            .map(CategoryDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("L'id de la categorie est nulle");
        }

        categoryRepository.deleteById(id);
    }

}
