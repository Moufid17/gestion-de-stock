package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.SalesLine;

public interface SalesLineRepository extends CrudRepository<SalesLine, Integer> {

}