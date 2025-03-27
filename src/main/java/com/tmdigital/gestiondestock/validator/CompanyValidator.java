package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.CompanyDto;

public class CompanyValidator {
    public static List<String> validate(CompanyDto companyDto) {
        List<String> errors = new ArrayList<>();

        if (companyDto == null) {
            errors.add("Veuillez renseigner le nom de la société");
            errors.add("Veuillez renseigner la description de la société");
            errors.add("Veuillez renseigner l'email de la société");
            errors.add("Veuillez renseigner le numéro de téléphone de la société");
            errors.add("Veuillez renseigner le code fiscal de la société");
            errors.add("Veuillez renseigner le site web de la société");
            errors.add("Veuillez renseigner l'adresse de la société");
            return errors;
        }

        if (companyDto.getName() == null || !StringUtils.hasLength(companyDto.getName())) {
            errors.add("Veuillez renseigner le nom de la société");
        }

        if (companyDto.getDescription() == null || !StringUtils.hasLength(companyDto.getDescription())) {
            errors.add("Veuillez renseigner la description de la société");
        }

        if (companyDto.getMail() == null || !StringUtils.hasLength(companyDto.getMail())) {
            errors.add("Veuillez renseigner l'email de la société");
        }

        if (companyDto.getNumTel() == null || !StringUtils.hasLength(companyDto.getNumTel())) {
            errors.add("Veuillez renseigner le numéro de téléphone de la société");
        }

        if (companyDto.getTaxCode() == null || !StringUtils.hasLength(companyDto.getTaxCode())) {
            errors.add("Veuillez renseigner le code fiscal de la société");
        }

        if (companyDto.getWebsite() == null || !StringUtils.hasLength(companyDto.getWebsite())) {
            errors.add("Veuillez renseigner le site web de la société");
        }

        errors.addAll(AddressValidator.validate(companyDto.getAddress()));
        
        return errors;
    }
}
