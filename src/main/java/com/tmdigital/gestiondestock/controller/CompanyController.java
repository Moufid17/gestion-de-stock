package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.CompanyApi;
import com.tmdigital.gestiondestock.dto.CompanyDto;
import com.tmdigital.gestiondestock.services.CompanyService;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public CompanyDto save(CompanyDto dto) {
        return companyService.save(dto);
    }

    @Override
    public CompanyDto findById(Integer id) {
        return companyService.findById(id);
    }

    @Override
    public List<CompanyDto> findAll() {
        return companyService.findAll();
    }

    @Override
    public void delete(Integer id) {
        companyService.delete(id);
    }
}
