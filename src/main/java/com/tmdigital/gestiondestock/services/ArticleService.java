package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.ArticleDto;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();
    
    List<ArticleDto> findAllArticleByCompany(Integer idCompany);
    
    List<ArticleDto> findAllArticleByCategory(Integer idCategory);
    
    void delete(Integer id);
}
