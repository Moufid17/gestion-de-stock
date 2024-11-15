package com.tmdigital.gestiondestock.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}