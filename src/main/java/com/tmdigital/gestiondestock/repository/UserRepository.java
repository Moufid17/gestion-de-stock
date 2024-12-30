package com.tmdigital.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Override
    List<User> findAll();

    @Query("select u from User u where u.company.id = :id")
    List<User> findAllByCompany(Integer id);
}