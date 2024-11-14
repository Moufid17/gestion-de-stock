package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.SalesDto;

public class SalesValidator {
    public static List<String> validate(SalesDto salesDto) {
        List<String> errors = new ArrayList<>();

        if (salesDto == null) {
            errors.add("Veuillez renseigner le code de la vente");
            errors.add("Veuillez renseigner la date de la vente");
            return errors;
        }

        if (!StringUtils.hasLength(salesDto.getCode())) {
            errors.add("Veuillez renseigner le code de la vente");
        }

        if (salesDto.getOrderDate() == null) {
            errors.add("Veuillez renseigner la date de la vente");
        }

        return errors;
    }
}
