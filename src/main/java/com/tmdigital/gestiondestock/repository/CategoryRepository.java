package com.tmdigital.gestiondestock.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Optional<Category> findCategoryByCode(String code);
}