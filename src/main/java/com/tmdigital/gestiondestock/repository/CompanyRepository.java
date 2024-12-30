package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    @Override
    List<Company> findAll();
}