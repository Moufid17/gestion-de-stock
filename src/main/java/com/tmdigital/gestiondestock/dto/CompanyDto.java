package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.Company;

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

    public static CompanyDto fromEntity(Company company) {
        if (company == null) {
            return null;
        }
        return CompanyDto.builder()
            .id(company.getId())
            .name(company.getName())
            .description(company.getDescription())
            .mail(company.getMail())
            .numTel(company.getNumTel())
            .taxCode(company.getTaxCode())
            .photo(company.getPhoto())
            .website(company.getWebsite())
            .address(AddressDto.fromEntity(company.getAddress()))
            .build();
    }

    public static Company toEntity(CompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        }
        Company company = new Company();
        company.setId(companyDto.getId());
        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setMail(companyDto.getMail());
        company.setNumTel(companyDto.getNumTel());
        company.setTaxCode(companyDto.getTaxCode());
        company.setPhoto(companyDto.getPhoto());
        company.setWebsite(companyDto.getWebsite());
        company.setAddress(AddressDto.toEntity(companyDto.getAddress()));
        return company;
    }
}
