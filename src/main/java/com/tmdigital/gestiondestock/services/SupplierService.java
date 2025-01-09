package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.SupplierDto;

public interface SupplierService {

    SupplierDto save(SupplierDto dto);

    SupplierDto findById(Integer id);

    List<SupplierDto> findAll();

    List<SupplierDto> findAllByCompany(Integer id);

    void delete(Integer id);
}
