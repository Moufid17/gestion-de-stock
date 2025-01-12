package com.tmdigital.gestiondestock.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.CompanyDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.User;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import com.tmdigital.gestiondestock.repository.UserRepository;
import com.tmdigital.gestiondestock.services.CompanyService;
import com.tmdigital.gestiondestock.validator.CompanyValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CompanyDto save(CompanyDto dto) {
       List<String> errors = CompanyValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("L'entité company n'est pas valide", ErrorCodes.COMPANY_NOT_VALID, errors);
        }
        return CompanyDto.fromEntity(companyRepository.save(CompanyDto.toEntity(dto)));
    }

    @Override
    public CompanyDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
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
            log.error("L'identifiant est nul");
            return;
        }

        List<User> users = userRepository.findAllByCompanyId(id);

        if (!users.isEmpty()) {
            log.error("Impossible de supprimer l'entreprise");
            throw new InvalidEntityException("Impossible de supprimer l'entreprise avec l'id = " + id, ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        companyRepository.deleteById(id);
    }

}
