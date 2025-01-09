package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.CompanyDto;

public interface CompanyService {

    CompanyDto save(CompanyDto dto);

    CompanyDto findById(Integer id);

    List<CompanyDto> findAll();

    void delete(Integer id);

}
