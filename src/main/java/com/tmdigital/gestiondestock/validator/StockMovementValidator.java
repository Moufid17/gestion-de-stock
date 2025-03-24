package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import com.tmdigital.gestiondestock.dto.StockMovementDto;

public class StockMovementValidator {

    public static List<String> validate(StockMovementDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Veuillez renseigner la quantité du mouvement de stock");
            errors.add("Veuillez renseigner l'article du mouvement de stock");
            errors.add("Veuillez renseigner le type du mouvement de stock");
            errors.add("Veuillez renseigner la source du mouvement de stock");
            errors.add("Veuillez renseigner la commande du mouvement de stock");
            return errors;
        }
        
        if (dto.getQty() == null) {
            errors.add("Veuillez renseigner la quantité du mouvement de stock");
        }
                
        if (dto.getArticle() == null || dto.getArticle().getId() == null) {
            errors.add("Veuillez renseigner l'article du mouvement de stock");
         }

        if (dto.getTypeMvt() == null) {
            errors.add("Veuillez renseigner le type du mouvement de stock");
        }

        if (dto.getSourceMvt() == null) {
            errors.add("Veuillez renseigner la source du mouvement de stock");
        }

        if (dto.getOrderId() == null) {
            errors.add("Veuillez renseigner la commande du mouvement de stock");
        }

        return errors;
    }

}
