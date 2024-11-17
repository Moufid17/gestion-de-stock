package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    Article findArticleByCode(String code);

    List<Article> findAllByCategoryId(Integer idCategory);
}