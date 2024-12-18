package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    Article findArticleByCode(String code);

    @Override
    List<Article> findAll();

    List<Article> findAllByCategoryId(Integer idCategory);

    List<Article> findAllByCompanyId(Integer idCategory);
}
