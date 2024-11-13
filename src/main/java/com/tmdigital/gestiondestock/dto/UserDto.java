package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.Company;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer id;
    
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String photo;

    private String numTel;

    private AddressDto address;

    @JsonIgnore
    private List<RolesDto> rules;

    private Company company;
}
