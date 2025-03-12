package com.tmdigital.gestiondestock.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.Utils.PasswordUtils;
import com.tmdigital.gestiondestock.dto.UserDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.NotFoundEntityException;
import com.tmdigital.gestiondestock.model.Company;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import com.tmdigital.gestiondestock.repository.UserRepository;
import com.tmdigital.gestiondestock.repository.RolesRepository;
import com.tmdigital.gestiondestock.services.UserService;
import com.tmdigital.gestiondestock.validator.UserValidator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RolesRepository rolesRepository;

    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDto save(UserDto dto) {
        List<String> errors = UserValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.USER_NOT_VALID, errors);
        }

        Optional<Company> company = companyRepository.findById(dto.getIdCompany());
        if (!company.isPresent()) {
            throw new NotFoundEntityException("Cette société n'existe pas.", ErrorCodes.COMPANY_NOT_FOUND);
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            log.error("L'utilisateur avec cet email existe déjà.");
            throw new InvalidEntityException("Il existe déjà un utilisateur avec l'email " + dto.getEmail(), ErrorCodes.USER_ALREADY_EXIST);
        }

        // Encrypt password
        dto.setPassword(PasswordUtils.encodePassword(dto.getPassword()));

        return UserDto.fromEntity(userRepository.save(UserDto.toEntity(dto, companyRepository, rolesRepository)));
    }

    @Override
    public UserDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return userRepository.findById(id)
            .map(UserDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucun utilisateur avec l'identifiant " + id + " n'a été trouvé.", ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public UserDto findByEmail(String email) {
        if (email == null || !StringUtils.hasLength(email)) {
            log.error("L'identifiant est nul");
            return null;
        }

        return userRepository.findByEmail(email)
            .map(UserDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucun utilisateur avec l'email " + email + " n'a été trouvé.", ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
            .map(UserDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllByCompany(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            // throw new InvalidOperationException("L'identifiant de la société ne doit pas être nul.", ErrorCodes.COMPANY_NOT_VALID);
            return Collections.emptyList();
        }

        return userRepository.findAllByCompanyId(id).stream()
            .map(UserDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        userRepository.deleteById(id);
    }
}
