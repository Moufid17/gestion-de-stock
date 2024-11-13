package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.Company;
import com.tmdigital.gestiondestock.model.User;

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

    public static UserDto fromEntity (User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .password(user.getPassword())
            .photo(user.getPhoto())
            .numTel(user.getNumTel())
            .address(AddressDto.fromEntity(user.getAddress()))
            .company(user.getCompany())
            .build();
    }

    public static User toEntity (UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoto(userDto.getPhoto());
        user.setNumTel(userDto.getNumTel());
        user.setAddress(AddressDto.toEntity(userDto.getAddress()));
        user.setCompany(userDto.getCompany());
        return user;
    }
}
