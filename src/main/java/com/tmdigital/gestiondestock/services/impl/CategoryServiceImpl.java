package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.CategoryDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.Article;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.CategoryRepository;
import com.tmdigital.gestiondestock.services.CategoryService;
import com.tmdigital.gestiondestock.validator.CategoryValidator;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
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
            log.error("L'identifiant est nul");
            return null;
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
            log.error("L'identifiant est nul");
            return null;
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
            log.error("L'identifiant est nul");
            return null;
        }

        return categoryRepository.findAllByIdCompany(idCompany).stream()
            .map(CategoryDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        List<Article> articles = articleRepository.findAllByCategoryId(id);

        if (!articles.isEmpty()) {
            log.error("Impossible de supprimer la categorie avec l'id = {}", id);
            throw new InvalidEntityException("Impossible de supprimer la categorie avec l'id =" + id , ErrorCodes.ARTICLE_CATEGORY_IS_ALREADY_USED);
        }

        categoryRepository.deleteById(id);
    }

}
