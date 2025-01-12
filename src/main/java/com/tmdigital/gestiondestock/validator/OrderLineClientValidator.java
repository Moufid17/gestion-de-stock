package com.tmdigital.gestiondestock.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderLineClientDto;

public class OrderLineClientValidator {
    public static List<String> validate(OrderLineClientDto orderLineClientDto) {
        List<String> errors = new ArrayList<>();

        if (orderLineClientDto == null) {
            errors.add("Veuillez renseigner l'article");
            errors.add("Veuillez renseigner le prix unitaire de vente TTC");
            errors.add("Veuillez renseigner la quantité");
            errors.add("Veuillez renseigner la commande client");
            return errors;
        }

        if (orderLineClientDto.getSellPriceInclTax() == null || orderLineClientDto.getSellPriceInclTax().compareTo(BigDecimal.ZERO) == 0) { 
            errors.add("Veuillez renseigner le prix unitaire de vente TTC");
        } else if (orderLineClientDto.getSellPriceInclTax().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Le prix de vente doit être valable");
        }

        if (orderLineClientDto.getQty() == null || orderLineClientDto.getQty().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantité");
        } else if (orderLineClientDto.getQty().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("La quantité doit être valable");
        }

        if (orderLineClientDto.getArticle() == null || orderLineClientDto.getArticle().getId() == null) {
            errors.add("Veuillez renseigner l'article");
        }

        if (orderLineClientDto.getOrderClient() == null || orderLineClientDto.getOrderClient().getId() == null) {
            errors.add("Veuillez renseigner la commande client");
        }

        return errors;
    }
}
