package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.UserDto;

public class UserValidator {

    public static List<String> validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();

        if (userDto == null) {
            errors.add("Veuillez renseigner le nom de l'utilisateur");
            errors.add("Veuillez renseigner le prenom de l'utilisateur");
            errors.add("Veuillez renseigner l'email de l'utilisateur");
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
            errors.add("Veuillez renseigner le numero de telephone de l'utilisateur");
            errors.add("Veuillez renseigner l'adresse de l'utilisateur");
            return errors;
        }

        if (!StringUtils.hasLength(userDto.getLastName())) {
            errors.add("Veuillez renseigner le prenom de l'utilisateur");
        }
        if (!StringUtils.hasLength(userDto.getFirstName())) {
            errors.add("Veuillez renseigner le nom de l'utilisateur");
        }
        if (!StringUtils.hasLength(userDto.getEmail())) {
            errors.add("Veuillez renseigner l'email de l'utilisateur");
        }
        if (!StringUtils.hasLength(userDto.getPassword())) {
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
        }
        if (!StringUtils.hasLength(userDto.getNumTel())) {
            errors.add("Veuillez renseigner le numero de telephone de l'utilisateur");
        }
        if (userDto.getAddress() == null) {
            errors.add("Veuillez renseigner l'adresse de l'utilisateur");
        } else {
            if (!StringUtils.hasLength(userDto.getAddress().getAddress1())) {
                errors.add("Veuillez renseigner l'adresse 1 de l'utilisateur");
            }
    
            if (!StringUtils.hasLength(userDto.getAddress().getCity())) {
                errors.add("Veuillez renseigner la ville de l'utilisateur");
            }
    
            if (!StringUtils.hasLength(userDto.getAddress().getZip())) {
                errors.add("Veuillez renseigner le code postal de la ville de l'utilisateur");
            }
    
            if (!StringUtils.hasLength(userDto.getAddress().getCountry())) {
                errors.add("Veuillez renseigner le pays de l'utilisateur");
            }
        }

        return errors;
    }
}
