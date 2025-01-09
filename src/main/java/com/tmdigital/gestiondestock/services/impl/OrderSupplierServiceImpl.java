package com.tmdigital.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;
import com.tmdigital.gestiondestock.exception.ErrorCodes;
import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.model.Supplier;
import com.tmdigital.gestiondestock.repository.OrderSupplierRepository;
import com.tmdigital.gestiondestock.repository.SupplierRepository;
import com.tmdigital.gestiondestock.services.OrderSupplierService;
import com.tmdigital.gestiondestock.validator.OrderSupplierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderSupplierServiceImpl implements OrderSupplierService {

    private OrderSupplierRepository orderSupplierRepository;
    private SupplierRepository supplierRepository;

    public OrderSupplierServiceImpl(OrderSupplierRepository orderSupplierRepository, SupplierRepository supplierRepository) {
        this.orderSupplierRepository = orderSupplierRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public OrderSupplierDto save(OrderSupplierDto dto) {
        List<String> errors = OrderSupplierValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("L'objet n'est pas valide {}", dto);
            throw new InvalidEntityException("La commande n'est pas valide", ErrorCodes.ORDER_SUPPLIER_NOT_VALID, errors);
        }

        Optional<Supplier> supplier = supplierRepository.findById(dto.getId());

        if (!supplier.isPresent()) {
            log.warn("L'identifiant {}  n'est pas valide", dto.getId());
            throw new InvalidEntityException("L'identifiant " + dto.getId() + " n'est pas valide", ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        return OrderSupplierDto.fromEntity(orderSupplierRepository.save(OrderSupplierDto.toEntity(dto)));
    }

    @Override
    public OrderSupplierDto findById(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderSupplierRepository.findById(id)
                .map(OrderSupplierDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException("L'identifiant " + id + " n'est pas valide.", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND));
    }

    @Override
    public OrderSupplierDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderSupplierRepository.findByCode(code)
                .map(OrderSupplierDto::fromEntity)
                .orElseThrow(() -> new InvalidEntityException("Le code " + code + " n'est pas valide.", ErrorCodes.ORDER_SUPPLIER_NOT_FOUND));
    }

    @Override
    public List<OrderSupplierDto> findAll() {
        return orderSupplierRepository.findAll().stream()
                .map(OrderSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSupplierDto> findAllByCompany(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderSupplierRepository.findAllByIdCompany(id).stream()
                .map(OrderSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderSupplierDto> findAllBySupplier(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return null;
        }

        return orderSupplierRepository.findAllBySupplierId(id).stream()
                .map(OrderSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("L'identifiant est nul");
            return;
        }

        orderSupplierRepository.deleteById(id);
    }
}
