package com.tmdigital.gestiondestock.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;

public class OrderLineSupplierValidator {
    public static List<String> validate(OrderLineSupplierDto orderLineSupplierDto) {
        List<String> errors = new ArrayList<>();

        if (orderLineSupplierDto == null) {
            errors.add("Veuillez renseigner l'article.");
            // errors.add("Veuillez renseigner le prix d'achat unitaire TTC.");
            errors.add("Veuillez renseigner la quantité.");
            // errors.add("Veuillez renseigner la commande fournisseur associée.");
            return errors;
        }

        // if (orderLineSupplierDto.getSellPriceInclTax() == null || orderLineSupplierDto.getSellPriceInclTax().compareTo(BigDecimal.ZERO) == 0) {
        //     errors.add("Veuillez renseigner le prix d'achat unitaire TTC.");
        // } else if (orderLineSupplierDto.getSellPriceInclTax().compareTo(BigDecimal.ZERO) < 0) {
        //     errors.add("Le prix d'achat doit être valable.");
        // }

        if (orderLineSupplierDto.getQty() == null || orderLineSupplierDto.getQty().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantité.");
        } else if (orderLineSupplierDto.getQty().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("La quantité doit être valable.");
        }

        if (orderLineSupplierDto.getArticle() == null || orderLineSupplierDto.getArticle().getId() == null) {
            errors.add("Veuillez renseigner l'article.");
        }

        // if (orderLineSupplierDto.getOrderSupplier() == null || orderLineSupplierDto.getOrderSupplier().getId() == null) {
        //     errors.add("Veuillez renseigner la commande fournisseur associée.");
        // }

        return errors;
    }
}
