package com.tmdigital.gestiondestock.services.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmdigital.gestiondestock.dto.CategoryDto;
import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.CategoryRepository;

import io.jsonwebtoken.lang.Collections;

import com.tmdigital.gestiondestock.model.Category;
import com.tmdigital.gestiondestock.model.Article;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryDto categoryDto;


    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);

       // Given
       categoryDto = CategoryDto.builder()
            .id(1)
            .code("Test Code")
            .designation("Test Designation")
            .idCompany(1)
            .build();
    }

    @Test
    public void shouldSaveCategorySuccessfully() {
        // Given
        // When
        when(categoryRepository.save(any(Category.class))).thenReturn(CategoryDto.toEntity(categoryDto));
        CategoryDto savedCategory = categoryService.save(categoryDto);

        // Then
        assertNotNull(savedCategory);
        assertEquals(categoryDto.getId(), savedCategory.getId());
        assertEquals(categoryDto.getCode(), savedCategory.getCode());
        assertEquals(categoryDto.getDesignation(), savedCategory.getDesignation());
        assertEquals(categoryDto.getIdCompany(), savedCategory.getIdCompany());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void shouldThrowInvalidExceptionWhenSavingCategoryWithInvalidData() {
        // Given
        CategoryDto categoryDto = CategoryDto.builder().build();

        // When
        // Then
        assertThrows(RuntimeException.class, () -> categoryService.save(categoryDto), "La category n'est pas valide");
    }

    @Test
    void shouldFindByIdCategorySuccessfully() {
        // Given
        Category Category = CategoryDto.toEntity(categoryDto);
        //when
        when(categoryRepository.findById(categoryDto.getId())).thenReturn(Optional.of(Category));
        CategoryDto foundCategory = categoryService.findById(categoryDto.getId());

        // Then
        assertNotNull(foundCategory);
        assertEquals(categoryDto, foundCategory);
        assertEquals(categoryDto.getId(), foundCategory.getId());
        assertEquals(categoryDto.getCode(), foundCategory.getCode());
        assertEquals(categoryDto.getDesignation(), foundCategory.getDesignation());
        assertEquals(categoryDto.getIdCompany(), foundCategory.getIdCompany());
        verify(categoryRepository, atMostOnce()).findById(categoryDto.getId());
    }

    @Test
    void shouldReturnNullWhenFindCategoryByIdWithNullId() {
        // Given
        // When
        // Then
        assertEquals(null, categoryService.findById(null));
    }

    @Test
    void shouldThrowExceptionWhenFindCategoryByIdWithUnexistingId() {
        // Given
        Integer unExistId = 0;

        // When
        when(categoryRepository.findById(unExistId)).thenReturn(Optional.empty());
        // Then
        assertThrows(RuntimeException.class, () -> categoryService.findById(unExistId), "La category avec l'id = " + unExistId);
    }

    @Test
    void shouldFindByCodeCategorySuccessfully() {
        // Given
        Category Category = CategoryDto.toEntity(categoryDto);
        //when
        when(categoryRepository.findCategoryByCode(categoryDto.getCode())).thenReturn(Optional.of(Category));
        CategoryDto foundCategory = categoryService.findByCode(categoryDto.getCode());

        // Then
        assertNotNull(foundCategory);
        assertEquals(categoryDto, foundCategory);
        assertEquals(categoryDto.getId(), foundCategory.getId());
        assertEquals(categoryDto.getCode(), foundCategory.getCode());
        assertEquals(categoryDto.getDesignation(), foundCategory.getDesignation());
        assertEquals(categoryDto.getIdCompany(), foundCategory.getIdCompany());
        verify(categoryRepository, atMostOnce()).findById(categoryDto.getId());
    }

    @Test
    void shouldReturnNullWhenFindCategoryByCodeWithNullId() {
        // Given
        // When
        // Then
        assertEquals(null, categoryService.findByCode(null));
    }

    @Test
    void shouldThrowExceptionWhenFindCategoryByCodeWithUnexistingId() {
        // Given
        String unExistId = "code";

        // When
        when(categoryRepository.findCategoryByCode(unExistId)).thenReturn(Optional.empty());
        // Then
        assertThrows(RuntimeException.class, () -> categoryService.findByCode(unExistId), "La category avec le code = " + unExistId + ", n'existe pas.");
    }

    @Test
    void shouldDeleteCategorySuccessfully() {
        // When
        when(articleRepository.findAllByCategoryId(categoryDto.getId())).thenReturn(Collections.emptyList());
        doNothing().when(categoryRepository).deleteById(categoryDto.getId());
        categoryService.delete(categoryDto.getId());

        // Then
        verify(articleRepository, times(1)).findAllByCategoryId(categoryDto.getId());
        verify(categoryRepository, times(1)).deleteById(categoryDto.getId());
    }

    @SuppressWarnings("null")
    @Test
    void shouldReturnVoidWhenDeleteCompanyWithNullId() {
        // Given
        // When
        // Then
        categoryService.delete(null);
        verify(articleRepository, times(0)).findAllByCategoryId(null);
        verify(categoryRepository, times(0)).deleteById(null);
    }

    @Test
    void shouldThrowInvalidEntityExceptionWhenDeleteCompanyHavingUsers() {
        // Given
        ArticleDto articleDto = ArticleDto.builder()
            .id(1)
            .code("Test Code")
            .designation("Test Designation")
            .buyPrice(BigDecimal.ONE)
            .sellPrice(BigDecimal.ONE)
            .vatRates(BigDecimal.ONE)
            .sellPriceInclTax(BigDecimal.valueOf(1))
            .photo("Test Photo")
            .category(categoryDto)
            .company(1)
        .build();

        Article article = ArticleDto.toEntity(articleDto);
        
        // When
        when(articleRepository.findAllByCategoryId(categoryDto.getId())).thenReturn(List.of(article));
        doNothing().when(categoryRepository).deleteById(categoryDto.getId());
        

        // Then
        assertThrows(RuntimeException.class, () -> categoryService.delete(categoryDto.getId()), "Impossible de supprimer la categorie avec l'id = " + categoryDto.getId());
        verify(articleRepository, times(1)).findAllByCategoryId(categoryDto.getId());
        verify(categoryRepository, times(0)).deleteById(categoryDto.getId());
    }
}
