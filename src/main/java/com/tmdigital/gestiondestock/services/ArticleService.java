package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.dto.OrderLineClientDto;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;
import com.tmdigital.gestiondestock.dto.SalesLineDto;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();
    
    List<ArticleDto> findAllArticleByCompany(Integer idCompany);
    
    List<ArticleDto> findAllArticleByCategory(Integer idCategory);

    List<SalesLineDto> findAllSalesLineByArticleId(Integer idArticle);
    
    List<OrderLineClientDto> findAllInOrderLineClientByArticleId(Integer idArticle);

    List<OrderLineSupplierDto> findAllOrderLineSupplierByArticleId(Integer idArticle);
    
    void delete(Integer id);
}
