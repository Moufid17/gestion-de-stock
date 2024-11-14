package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.SupplierDto;

public class SupplierValidator {
    public static List<String> validate(SupplierDto supplierDto) {
        List<String> errors = new ArrayList<>();

        if (supplierDto == null) {
            errors.add("Veuillez renseigner le nom du fournisseur.");
            errors.add("Veuillez renseigner le prénom du fournisseur.");
            errors.add("Veuillez renseigner l'email du fournisseur.");
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur.");
            errors.add("Veuillez renseigner l'adresse du fournisseur.");
            return errors;
        }

        if (!StringUtils.hasLength(supplierDto.getFirstname())) {
            errors.add("Veuillez renseigner le prénom du fournisseur.");
        }

        if (!StringUtils.hasLength(supplierDto.getLastname())) {
            errors.add("Veuillez renseigner le nom du fournisseur.");
        }

        if (!StringUtils.hasLength(supplierDto.getEmail())) {
            errors.add("Veuillez renseigner l'email du fournisseur.");
        }

        if (!StringUtils.hasLength(supplierDto.getPhonenumber())) {
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur.");
        }

        errors.addAll(AddressValidator.validate(supplierDto.getAddress()));

        return errors;
    }
}
