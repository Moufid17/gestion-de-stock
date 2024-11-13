package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDto {

    private Integer id;
    
    private String name;

    private String description;

    private String mail;

    private String numTel;

    private String taxCode;

    private String photo;

    private String website;

    private AddressDto address;

    @JsonIgnore
    private List<UserDto> users;
}
