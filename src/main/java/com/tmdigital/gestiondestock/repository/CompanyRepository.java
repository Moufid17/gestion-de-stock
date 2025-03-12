package com.tmdigital.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    Optional<Company> findByName(String name);

    Optional<Company> findByMail(String mail);

    @Override
    List<Company> findAll();
}