package com.tmdigital.gestiondestock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.RolesDto;

public class RolesValidator {
    public static List<String> validate(RolesDto clientDto) {
        List<String> errors = new ArrayList<>();

        if (clientDto == null || !StringUtils.hasLength(clientDto.getRoleName())) {
            errors.add("Veuillez renseigner le nom du role");
        }
        return errors;
    }
}
