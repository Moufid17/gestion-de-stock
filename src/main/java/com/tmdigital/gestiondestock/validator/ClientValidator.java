package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.ClientDto;

public class ClientValidator {
    public static List<String> validate(ClientDto clientDto) {
        List<String> errors = new ArrayList<>();

        if (clientDto == null) {
            errors.add("Veuillez renseigner le nom du client");
            errors.add("Veuillez renseigner le prénom du client");
            errors.add("Veuillez renseigner l'email du client");
            errors.add("Veuillez renseigner le numéro de téléphone du client");
            errors.add("Veuillez renseigner l'adresse du client");
            return errors;
        }

        if (!StringUtils.hasLength(clientDto.getFirstname())) {
            errors.add("Veuillez renseigner le prénom du client");
        }

        if (!StringUtils.hasLength(clientDto.getLastname())) {
            errors.add("Veuillez renseigner le nom du client");
        }

        if (!StringUtils.hasLength(clientDto.getEmail())) {
            errors.add("Veuillez renseigner l'email du client");
        }

        if (!StringUtils.hasLength(clientDto.getPhonenumber())) {
            errors.add("Veuillez renseigner le numéro de téléphone du client");
        }

        errors.addAll(AddressValidator.validate(clientDto.getAddress()));
        
        return errors;
    }
}
