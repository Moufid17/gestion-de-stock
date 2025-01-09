package com.tmdigital.gestiondestock.services;

import java.util.List;

import com.tmdigital.gestiondestock.dto.SalesDto;

public interface SalesService {

    SalesDto save(SalesDto dto);

    SalesDto findById(Integer id);

    SalesDto findByCode(String code);

    List<SalesDto> findAllByCompany(Integer id);

    List<SalesDto> findAll();

    void delete(Integer id);
}
