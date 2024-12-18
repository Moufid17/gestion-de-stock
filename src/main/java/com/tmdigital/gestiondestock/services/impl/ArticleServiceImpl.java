package com.tmdigital.gestiondestock.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

    public ArticleDto save(ArticleDto dto) {
        return null;
    };

    public ArticleDto findById(Integer id) {
        return null;
    };

    public ArticleDto findByCodeArticle(String codeArticle) {
        return null;
    };

    public List<ArticleDto> findAll() {
        return null;
    };
    
    public List<ArticleDto> findAllArticleByCompany(Integer idCompany) {
        return null;
    };
    
    public List<ArticleDto> findAllArticleByCategory(Integer idCategory) {
        return null;
    };
    
    public void delete(Integer id) {
    };
}
