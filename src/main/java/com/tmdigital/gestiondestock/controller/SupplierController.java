package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.SupplierApi;
import com.tmdigital.gestiondestock.dto.SupplierDto;
import com.tmdigital.gestiondestock.services.SupplierService;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController implements SupplierApi {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public SupplierDto save(SupplierDto dto) {
        return supplierService.save(dto);
    }

    @Override
    public SupplierDto findById(Integer id) {
        return supplierService.findById(id);
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierService.findAll();
    }

    @Override
    public List<SupplierDto> findAllByCompany(Integer id) {
        return supplierService.findAllByCompany(id);
    }

    @Override
    public void delete(Integer id) {
        supplierService.delete(id);
    }
}
