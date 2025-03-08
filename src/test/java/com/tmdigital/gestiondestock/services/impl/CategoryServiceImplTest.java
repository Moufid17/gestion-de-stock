package com.tmdigital.gestiondestock.services.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmdigital.gestiondestock.dto.CategoryDto;
import com.tmdigital.gestiondestock.repository.ArticleRepository;
import com.tmdigital.gestiondestock.repository.CategoryRepository;
import com.tmdigital.gestiondestock.model.Category;

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

}
