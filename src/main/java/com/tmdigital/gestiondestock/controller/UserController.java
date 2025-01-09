package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.UserApi;
import com.tmdigital.gestiondestock.dto.UserDto;
import com.tmdigital.gestiondestock.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDto save(UserDto dto) {
        return userService.save(dto);
    }

    @Override
    public UserDto findById(Integer id) {
        return userService.findById(id);
    }

    @Override
    public UserDto findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Override
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @Override
    public List<UserDto> findAllByCompany(Integer idCompany) {
        return userService.findAllByCompany(idCompany);
    }

    @Override
    public void delete(Integer id) {
        userService.delete(id);
    }


}
