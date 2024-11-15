package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {

}