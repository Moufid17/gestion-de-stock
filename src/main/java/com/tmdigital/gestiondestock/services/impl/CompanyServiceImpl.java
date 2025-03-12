package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmdigital.gestiondestock.dto.CompanyDto;
import com.tmdigital.gestiondestock.Utils.PasswordUtils;
import com.tmdigital.gestiondestock.dto.AddressDto;
import com.tmdigital.gestiondestock.dto.UserDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.InvalidOperationException;
import com.tmdigital.gestiondestock.model.User;
import com.tmdigital.gestiondestock.model.Roles;
import com.tmdigital.gestiondestock.repository.CompanyRepository;
import com.tmdigital.gestiondestock.repository.RolesRepository;
import com.tmdigital.gestiondestock.repository.UserRepository;
import com.tmdigital.gestiondestock.services.CompanyService;
import com.tmdigital.gestiondestock.validator.CompanyValidator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    private RolesRepository rolesRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository, RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
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

        if (dto.getId() != null) {
            if (checkIfCompanyExistById(dto.getId())) {
                log.error("La société existe déjà.");
                throw new InvalidEntityException("La société existe déjà.", ErrorCodes.COMPANY_ALREADY_EXIST);
            }
        };

        if (checkIfCompanyExistByEmail(dto.getMail())) {
            log.error("La société existe déjà. mail (incorrect) = {}", dto.getMail());
            throw new InvalidEntityException("La société existe déjà.", ErrorCodes.COMPANY_ALREADY_EXIST);
        }
        
        Optional<Roles> role = rolesRepository.findByRoleName("cmp_admin");
        if (role.isEmpty()) {
            log.error("(save) Erreur lors de la récupération du rôle.");
            throw new InvalidOperationException("Erreur lors de la création de la société. Contacter le service assistance. Merci", ErrorCodes.COMPANY_NOT_VALID);
        }

        // Save company
        CompanyDto savedCompanyDto = CompanyDto.fromEntity(companyRepository.save(CompanyDto.toEntity(dto)));
        // Create user admin dto
        UserDto userDto = generateAdminCompanyUserDto(savedCompanyDto, List.of(role.get().getRoleName()));
        
        final String adminPassword = userDto.getPassword();
        userDto.setPassword(PasswordUtils.encodePassword(adminPassword));
        // Save user
        userRepository.save(UserDto.toEntity(userDto, companyRepository, rolesRepository));
        
        log.info("L'utilisateur a été créé avec succès. Son mot de passe est {}", adminPassword);
        // [ ] Send an email to the user with his password
        
        return savedCompanyDto;
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
            throw new InvalidOperationException("Impossible de supprimer l'entreprise avec l'id = " + id, ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        companyRepository.deleteById(id);
    }


    private boolean checkIfCompanyExistById(Integer id) {
        return companyRepository.findById(id).isPresent();
        // if (companyRepository.findById(id).isPresent()) {
        //     log.error("La société existe déjà.");
        //     throw new InvalidEntityException("La société existe déjà.", ErrorCodes.COMPANY_ALREADY_EXIST);
        // }
    }

    private boolean checkIfCompanyExistByEmail(String mail) {
        return companyRepository.findByMail(mail).isPresent();
        // if (companyRepository.findByMail(mail).isPresent()) {
        //     log.error("La société existe déjà. mail (incorrect) = {}", mail);
        //     throw new InvalidEntityException("La société existe déjà.", ErrorCodes.COMPANY_ALREADY_EXIST);
        // }
    }

    private UserDto generateAdminCompanyUserDto(CompanyDto dto, List<String> roles) {
        UserDto userDto = UserDto.builder()
                .firstName(dto.getName() + "_Admin")
                .lastName(dto.getName().toUpperCase())
                .email(dto.getMail())
                .numTel(dto.getNumTel())
                .password(PasswordUtils.generatePassword())
                .address(AddressDto.builder()
                    .address1(dto.getAddress().getAddress1())
                    .address2(dto.getAddress().getAddress2())
                    .city(dto.getAddress().getCity())
                    .state(dto.getAddress().getState())
                    .zip(dto.getAddress().getZip())
                    .country(dto.getAddress().getCountry())
                    .build())
                .roles(roles)
                .idCompany(dto.getId())
            .build();
        return userDto;
    }

    @Transactional
    private User createUserWithRoles(UserDto userDto, Optional<Roles> role) {
        User user = userRepository.save(UserDto.toEntity(userDto, companyRepository, rolesRepository));
        user.getRoles().add(role.get());
        return userRepository.save(user);
    }

}
