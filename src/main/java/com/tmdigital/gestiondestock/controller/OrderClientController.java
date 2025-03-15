package com.tmdigital.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.OrderClientApi;
import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.model.OrderStatus;
import com.tmdigital.gestiondestock.services.OrderClientService;

@RestController
@RequestMapping("/api/v1/orderclients")
public class OrderClientController implements OrderClientApi {

    private OrderClientService orderClientService;

    public OrderClientController(OrderClientService orderClientService) {
        this.orderClientService = orderClientService;
    }

    @Override
    public ResponseEntity<Void> updateStatus(Integer id, OrderStatus orderStatus) {
        orderClientService.updateOrderStatus(id, orderStatus);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<OrderClientDto> updateQte(Integer id, Integer orderLineId, BigDecimal newQte) {
        orderClientService.updateOrderLineQte(id, orderLineId, newQte);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<OrderClientDto> save(OrderClientDto dto) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<OrderClientDto> findById(Integer id) {
        return ResponseEntity.ok(orderClientService.findById(id));
    }

    @Override
    public ResponseEntity<OrderClientDto> findByCode(String code) {
        return ResponseEntity.ok(orderClientService.findByCode(code));
    }

    @Override
    public  ResponseEntity<List<OrderClientDto>> findAllByClientId(Integer id) {
        return ResponseEntity.ok(orderClientService.findAllByClient(id));
    }

    @Override
    public ResponseEntity<List<OrderClientDto>> findAllByCompany(Integer id) {
        return ResponseEntity.ok(orderClientService.findAllByCompany(id));
    }

    @Override
    public ResponseEntity<List<OrderClientDto>> findAll() {
        return ResponseEntity.ok(orderClientService.findAll());
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        orderClientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
