package com.tmdigital.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tmdigital.gestiondestock.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    List<User> findAllByCompanyId(Integer id);
}