package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmdigital.gestiondestock.dto.SupplierDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.OrderSupplier;
import com.tmdigital.gestiondestock.repository.OrderSupplierRepository;
import com.tmdigital.gestiondestock.repository.SupplierRepository;
import com.tmdigital.gestiondestock.services.SupplierService;
import com.tmdigital.gestiondestock.validator.SupplierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final OrderSupplierRepository orderSupplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository, OrderSupplierRepository orderSupplierRepository) {
        this.supplierRepository = supplierRepository;
        this.orderSupplierRepository = orderSupplierRepository;
    }

    @Override
    public SupplierDto save(SupplierDto dto) {
        List<String> errors = SupplierValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.SUPPLIER_NOT_VALID, errors);
        }

        return SupplierDto.fromEntity(supplierRepository.save(SupplierDto.toEntity(dto)));
    }

    @Override
    public SupplierDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
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
            log.error("L'identifiant est nul");
            return null;
        }

        return supplierRepository.findAllByIdCompany(id)
                .stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        List<OrderSupplier> ordersSupplier = orderSupplierRepository.findAllBySupplierId(id);

        if (!ordersSupplier.isEmpty()) {
            log.warn("Le fournisseur avec l'ID {} ne peut pas être supprimé car il est lié à des commandes", id);
            throw new InvalidEntityException("Impossible de supprimer le fournisseur car il est lié à des commandes", ErrorCodes.SUPPLIER_ALREADY_IN_USE);
        }
        
        supplierRepository.deleteById(id);
    }

}
