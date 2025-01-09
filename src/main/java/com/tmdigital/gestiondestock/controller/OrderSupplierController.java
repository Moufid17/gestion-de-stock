package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.OrderSupplierApi;
import com.tmdigital.gestiondestock.dto.OrderSupplierDto;
import com.tmdigital.gestiondestock.services.impl.OrderSupplierServiceImpl;

@RestController
@RequestMapping("/api/v1/ordersuppliers")
public class OrderSupplierController implements OrderSupplierApi {

    private OrderSupplierServiceImpl orderSupplierService;

    public OrderSupplierController(OrderSupplierServiceImpl orderSupplierService) {
        this.orderSupplierService = orderSupplierService;
    }

    @Override
    public OrderSupplierDto save(OrderSupplierDto dto) {
        return orderSupplierService.save(dto);
    }

    @Override
    public OrderSupplierDto findById(Integer id) {
        return orderSupplierService.findById(id);
    }

    @Override
    public OrderSupplierDto findByCode(String code) {
        return orderSupplierService.findByCode(code);
    }

    @Override
    public List<OrderSupplierDto> findAll() {
        return orderSupplierService.findAll();
    }

    @Override
    public List<OrderSupplierDto> findAllByCompany(Integer id) {
        return orderSupplierService.findAllByCompany(id);
    }

    @Override
    public List<OrderSupplierDto> findAllBySupplier(Integer id) {
        return orderSupplierService.findAllBySupplier(id);
    }

    @Override
    public void delete(Integer id) {
        orderSupplierService.delete(id);
    }


}
