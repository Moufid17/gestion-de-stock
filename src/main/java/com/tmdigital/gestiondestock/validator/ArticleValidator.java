package com.tmdigital.gestiondestock.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.ArticleDto;


public class ArticleValidator {
    public static List<String> validate(ArticleDto articleDto) {
        List<String> errors = new ArrayList<>();

        if (articleDto == null) {
            errors.add("Veuillez renseigner le code de l'article");
            errors.add("Veuillez renseigner la designation de l'article");
            errors.add("Veuillez renseigner le prix unitaire HT de l'article");
            errors.add("Veuillez renseigner le taux de TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
            errors.add("Veuillez renseigner le stock actuelle de l'article");
            errors.add("Veuillez renseigner la categorie de l'article");
            return errors;
        }

        if (!StringUtils.hasLength(articleDto.getCode())) {
            errors.add("Veuillez renseigner le code de l'article");
        }

        if (!StringUtils.hasLength(articleDto.getDesignation())) {
            errors.add("Veuillez renseigner la designation de l'article");
        }

        if (articleDto.getBuyPrice() == null || articleDto.getBuyPrice().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner le prix unitaire HT de l'article");
        } else if (articleDto.getBuyPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Le prix d'achat doit être valable");
        }

        if (articleDto.getSellPrice() == null || articleDto.getSellPrice().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        } else if (articleDto.getSellPrice().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Le prix de vente doit être valable");
        }

        if (articleDto.getVatRates() == null || articleDto.getVatRates().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner le taux de TVA de l'article");
        } else if (articleDto.getVatRates().compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Le taux de TVA doit être valable");
        }

        if (articleDto.getCategory() == null || articleDto.getCategory().getId() == null) {
            errors.add("Veuillez renseigner la catégorie de l'article");
        }

        return errors;
    }
}
