package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "User", description = "The user API")
public interface UserApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    UserDto save(@RequestBody UserDto dto);

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> findAll();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive an user", description = "Allow to retreive an user by his id", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive an user with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
        }
    )
    UserDto findById(@PathVariable Integer id);

    @GetMapping(value="/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto findByEmail(@PathVariable("email") String email);

    @GetMapping(value = "/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDto> findAllByCompany(@PathVariable("id") Integer idCompany);

    @DeleteMapping(value = "/{id}")
    void delete(@PathVariable Integer id);
}
