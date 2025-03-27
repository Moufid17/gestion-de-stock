package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.OrderClientDto;

public class OrderClientValidator {
    
    public static List<String> validate(OrderClientDto orderClientDto) {
        List<String> errors = new ArrayList<>();

        if (orderClientDto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner le client de la commande");
            errors.add("Veuillez renseigner les données de l'auteur de la commande"); 
            return errors;
        }
        
        if (!StringUtils.hasLength(orderClientDto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }

        if (orderClientDto.getIdCompany() == null ) {
            errors.add("Veuillez renseigner les données de l'auteur de la commande");
        }

        if (orderClientDto.getClient() == null || orderClientDto.getClient().getId() == null) {
            errors.add("Veuillez renseigner le client de la commande");
        }

        return errors;
    }
}
