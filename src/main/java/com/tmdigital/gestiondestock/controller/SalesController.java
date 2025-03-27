package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.SalesApi;
import com.tmdigital.gestiondestock.dto.SalesDto;
import com.tmdigital.gestiondestock.services.SalesService;
@RestController
@RequestMapping("/api/v1/sales")
public class SalesController implements SalesApi {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @Override
    public SalesDto save(SalesDto dto) {
        return salesService.save(dto);
    }

    @Override
    public SalesDto findById(Integer id) {
        return salesService.findById(id);
    }

    @Override
    public SalesDto findByCode(String code) {
        return salesService.findByCode(code);
    }

    @Override
    public List<SalesDto> findAll() {
        return salesService.findAll();
    }

    @Override
    public List<SalesDto> findAllByCompanyId(Integer id) {
        return salesService.findAllByCompany(id);
    }

    @Override
    public void delete(Integer id) {
        salesService.delete(id);
    }

}
