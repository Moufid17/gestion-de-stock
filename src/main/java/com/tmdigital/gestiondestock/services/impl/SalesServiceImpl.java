package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.SalesDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.repository.SalesRepository;
import com.tmdigital.gestiondestock.services.SalesService;
import com.tmdigital.gestiondestock.validator.SalesValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {

    private SalesRepository salesRepository;

    public SalesServiceImpl(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Override
    public SalesDto save(SalesDto dto) {
        List<String> errors = SalesValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("La commande vente n'est pas valide", ErrorCodes.SALES_NOT_VALID, errors);
        }

        return SalesDto.fromEntity(salesRepository.save(SalesDto.toEntity(dto)));
    }

    @Override
    public SalesDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return salesRepository.findById(id)
            .map(SalesDto::fromEntity)
            .orElseThrow(() -> new InvalidEntityException("Aucune commande de vente n'a été trouvée avec l'identifiant " + id));
    }

    @Override
    public SalesDto findByCode(String code) {
        if (code == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return salesRepository.findSalesByCode(code).map(SalesDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException("Aucune commande de vente n'a été trouvée avec le code " + code));
    }

    @Override
    public List<SalesDto> findAllByCompany(Integer idCompany) {
        if (idCompany == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return salesRepository.findAllByIdCompany(idCompany).stream()
            .map(SalesDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<SalesDto> findAll() {
        return salesRepository.findAll().stream()
            .map(SalesDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }
        salesRepository.deleteById(id);
    }

}
