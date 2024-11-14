package com.tmdigital.gestiondestock.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tmdigital.gestiondestock.dto.SalesLineDto;

public class SalesLineValidator {
    public static List<String> validate(SalesLineDto salesLineDto) {
        List<String> errors = new ArrayList<>();

        if (salesLineDto == null) {
            errors.add("Veuillez renseigner l'article");
            errors.add("Veuillez renseigner le prix unitaire de vente");
            errors.add("Veuillez renseigner la quantité");
            return errors;
        }

        if (salesLineDto.getArticle() == null || salesLineDto.getArticle().getId() == null) {
            errors.add("Veuillez renseigner l'article");
        }

        if (salesLineDto.getSellPriceInclTax() == null || salesLineDto.getSellPriceInclTax().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner le prix unitaire de vente");
        } else if (salesLineDto.getSellPriceInclTax().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Le prix unitaire de vente doit valable");
        }

        if (salesLineDto.getQty() == null || salesLineDto.getQty().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantité");
        } else if (salesLineDto.getQty().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("La quantité doit être valable");
        }

        return errors;
    }
}