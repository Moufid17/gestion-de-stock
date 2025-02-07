package com.tmdigital.gestiondestock.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.OrderClientApi;
import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.services.OrderClientService;

@RestController
@RequestMapping("/api/v1/orderclients")
public class OrderClientController implements OrderClientApi {

    private OrderClientService orderClientService;

    public OrderClientController(OrderClientService orderClientService) {
        this.orderClientService = orderClientService;
    }

    @Override
    public OrderClientDto save(OrderClientDto dto) {
        return orderClientService.save(dto);
    }

    @Override
    public OrderClientDto findById(Integer id) {
        return orderClientService.findById(id);
    }

    @Override
    public OrderClientDto findByCode(String code) {
        return orderClientService.findByCode(code);
    }

    @Override
    public List<OrderClientDto> findAllByClientId(Integer id) {
        return orderClientService.findAllByClient(id);
    }

    @Override
    public List<OrderClientDto> findAllByCompany(Integer id) {
        return orderClientService.findAllByCompany(id);
    }

    @Override
    public List<OrderClientDto> findAll() {
        return orderClientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        orderClientService.delete(id);
    }


}
