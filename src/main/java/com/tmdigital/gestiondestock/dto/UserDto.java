package com.tmdigital.gestiondestock.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.Company;
import com.tmdigital.gestiondestock.model.User;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import com.tmdigital.gestiondestock.repository.RolesRepository;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer id;
    
    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    private String photo;

    private String numTel;

    private AddressDto address;

    @JsonIgnore
    private List<String> roles;

    private Integer idCompany;

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
            .idCompany(user.getCompany().getId())
            .build();
    }

    public static User toEntity (UserDto userDto, CompanyRepository companyRepository, RolesRepository rolesRepository) {
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
        if (userDto.getRoles() != null) {
            user.setRoles(userDto.getRoles().stream()
                .map(roleName -> rolesRepository.findByRoleName(roleName))
                .filter(role -> role.isPresent())
                .map(role -> role.get())
                .collect(Collectors.toList()));
        } else {
            user.setRoles(rolesRepository.findByRoleName("cmp_default").stream().collect(Collectors.toList()));
        }
        Optional<Company> company = companyRepository.findById(userDto.getIdCompany());
        if (company.isPresent()) user.setCompany(company.get());
        return user;
    }
}
