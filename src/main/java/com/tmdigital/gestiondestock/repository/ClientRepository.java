package com.tmdigital.gestiondestock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    @Override
    List<Client> findAll();

    List<Client> findAllByCompany(Integer idCompany);
}