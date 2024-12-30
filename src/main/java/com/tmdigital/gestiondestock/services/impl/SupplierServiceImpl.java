package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.SupplierDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.repository.SupplierRepository;
import com.tmdigital.gestiondestock.services.SupplierService;
import com.tmdigital.gestiondestock.validator.SupplierValidator;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierDto save(SupplierDto dto) {
        List<String> errors = SupplierValidator.validate(dto);

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.SUPPLIER_NOT_VALID, errors);
        }

        return SupplierDto.fromEntity(supplierRepository.save(SupplierDto.toEntity(dto)));
    }

    @Override
    public SupplierDto findById(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'est fourni");
        }

        return supplierRepository.findById(id)
                .map(SupplierDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException("Aucun fournisseur avec l'ID = " + id + " n'a été trouvé.", ErrorCodes.SUPPLIER_NOT_FOUND));
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierDto> findAllByCompany(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant de la société n'est fourni");
        }

        return supplierRepository.findAllByIdCompany(id)
                .stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("Aucun identifiant n'est fourni");
        }
        this.supplierRepository.deleteById(id);
    }

}
