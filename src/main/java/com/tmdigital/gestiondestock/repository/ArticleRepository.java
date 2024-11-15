package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

}