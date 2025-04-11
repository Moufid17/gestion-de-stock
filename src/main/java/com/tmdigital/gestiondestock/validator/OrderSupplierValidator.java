package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;

public class OrderSupplierValidator {
    public static List<String> validate(OrderSupplierDto orderSupplierDto) {
        List<String> errors = new ArrayList<>();

        if (null == orderSupplierDto) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner le fournisseur de la commande"); 
            return errors;
        }
        
        if (!StringUtils.hasLength(orderSupplierDto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }

        if (orderSupplierDto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }

        if (null == orderSupplierDto.getSupplier() || null == orderSupplierDto.getSupplier().getId()) {
            errors.add("Veuillez renseigner le fournisseur de la commande");
        }

        if (null == orderSupplierDto.getCompanyId()) {
            errors.add("Veuillez renseigner l'identifiant de la société");
        }

        return errors;
    }
}
