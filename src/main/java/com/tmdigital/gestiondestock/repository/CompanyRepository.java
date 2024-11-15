package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

}