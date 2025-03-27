package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.CategoryDto;


public class CategoryValidator {
    
        public static List<String> validate(CategoryDto categoryDto) {
            List<String> errors = new ArrayList<>();

            if (categoryDto == null) {
                errors.add("Veuillez renseigner le code de la catégorie");
                errors.add("Veuillez renseigner la désignaton de la catégorie");
                errors.add("Veuillez renseigner l'entreprise associé à la catégorie");
                return errors;
            }

            if (categoryDto.getCode() == null || !StringUtils.hasLength(categoryDto.getCode())) {
                errors.add("Veuillez renseigner le code de la catégorie");
            }

            if (categoryDto.getDesignation() == null || !StringUtils.hasLength(categoryDto.getDesignation())) {
                errors.add("Veuillez renseigner la désignaton de la catégorie");
            }
            
            if (categoryDto.getIdCompany() == null) {
                errors.add("Veuillez renseigner l'entreprise associé à la catégorie");
            }
            
            return errors;
        }
}
