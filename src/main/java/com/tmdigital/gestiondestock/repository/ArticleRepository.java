package com.tmdigital.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    Optional<Article> findArticleByCode(String code);

    @Override
    List<Article> findAll();

    List<Article> findAllByCategoryId(Integer idCategory);

    List<Article> findAllByCompany(Integer company);
}
