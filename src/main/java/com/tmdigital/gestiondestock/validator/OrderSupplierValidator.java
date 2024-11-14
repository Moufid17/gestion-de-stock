package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;

public class OrderSupplierValidator {
    public static List<String> validate(OrderSupplierDto OrderSupplierDto) {
        List<String> errors = new ArrayList<>();

        if (OrderSupplierDto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner le fournisseur de la commande"); 
            return errors;
        }
        
        if (!StringUtils.hasLength(OrderSupplierDto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }

        if (OrderSupplierDto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }

        if (OrderSupplierDto.getSupplier() == null || OrderSupplierDto.getSupplier().getId() == null) {
            errors.add("Veuillez renseigner le fournisseur de la commande");
        }

        return errors;
    }
}
