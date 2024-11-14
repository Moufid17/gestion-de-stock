package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.AddressDto;

public class AddressValidator {
    public static List<String> validate(AddressDto addressDto) {
        List<String> errors = new ArrayList<>();

        if (addressDto == null) {
            errors.add("Veuillez renseigner l'adresse 1.");
            errors.add("Veuillez renseigner le nom de la ville.");
            errors.add("Veuillez renseigner le code postal");
            errors.add("Veuillez renseigner le pays");
            return errors;
        }
        
        if (!StringUtils.hasLength(addressDto.getAddress1())) {
            errors.add("Veuillez renseigner l'adresse 1.");
        }
        
        if (!StringUtils.hasLength(addressDto.getCity())) {
            errors.add("Veuillez renseigner le nom de la ville.");
        }
        
        if (!StringUtils.hasLength(addressDto.getZip())) {
            errors.add("Veuillez renseigner le code postal");
        }
        
        if (!StringUtils.hasLength(addressDto.getCountry())) {
            errors.add("Veuillez renseigner le pays");
        }

        return errors;
    }
}
