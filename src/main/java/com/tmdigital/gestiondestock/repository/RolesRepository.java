package com.tmdigital.gestiondestock.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tmdigital.gestiondestock.model.Roles;

public interface RolesRepository extends CrudRepository<Roles, Integer> {

    Optional<Roles> findByRoleName(String roleName);
}