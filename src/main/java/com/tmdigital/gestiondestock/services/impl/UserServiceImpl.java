package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.UserDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.Company;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import com.tmdigital.gestiondestock.repository.UserRepository;
import com.tmdigital.gestiondestock.services.UserService;
import com.tmdigital.gestiondestock.validator.UserValidator;

import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDto save(UserDto dto) {
        List<String> errors = UserValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.USER_NOT_VALID, errors);
        }

        Optional<Company> company = companyRepository.findById(dto.getCompany().getId());
        if (!company.isPresent()) {
            throw new InvalidEntityException("Aucune société a'existe pas.", ErrorCodes.COMPANY_NOT_FOUND);
        }

        return UserDto.fromEntity(userRepository.save(UserDto.toEntity(dto)));
    }

    @Override
    public UserDto findById(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'a été fourni");
        }

        return userRepository.findById(id)
            .map(UserDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucun utilisateur avec l'identifiant " + id + " n'a été trouvé.", ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public UserDto findByEmail(String email) {
        if (email == null || !StringUtils.hasLength(email)) {
            throw new InvalidEntityException("Aucun email n'a été fourni");
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
            throw new InvalidEntityException("Aucun identifiant de la société n'a été fourni");
        }

        return userRepository.findAllByCompanyId(id).stream()
            .map(UserDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'a été fourni");
        }

        userRepository.deleteById(id);
    }


}
