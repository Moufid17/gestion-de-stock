package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.UserDto;

public interface UserService {
    UserDto save(UserDto dto);

    UserDto findById(Integer id);

    UserDto findByEmail(String email);

    List<UserDto> findAll();
    
    List<UserDto> findAllByCompany(Integer id);

    void delete(Integer id);
}
