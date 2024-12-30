package com.tmdigital.gestiondestock.services.impl;

import java.util.List;

import com.tmdigital.gestiondestock.dto.CompanyDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import com.tmdigital.gestiondestock.services.CompanyService;
import com.tmdigital.gestiondestock.validator.CompanyValidator;

public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto save(CompanyDto dto) {
       List<String> errors = CompanyValidator.validate(dto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("L'entité company n'est pas valide", ErrorCodes.COMPANY_NOT_VALID, errors);
        }
        return CompanyDto.fromEntity(companyRepository.save(CompanyDto.toEntity(dto)));
    }

    @Override
    public CompanyDto findById(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'a été fourni");
        }

        return companyRepository.findById(id)
                .map(CompanyDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException(
                        "Aucune entreprise n'a été trouvée avec l'ID = " + id,
                        ErrorCodes.COMPANY_NOT_FOUND)
                );
    }

    @Override
    public List<CompanyDto> findAll() {
        return companyRepository.findAll().stream()
                .map(CompanyDto::fromEntity)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'a été fourni");
        }
        companyRepository.deleteById(id);
    }

}
