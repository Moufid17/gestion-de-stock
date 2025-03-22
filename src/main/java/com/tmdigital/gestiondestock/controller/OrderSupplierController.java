package com.tmdigital.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmdigital.gestiondestock.controller.api.OrderSupplierApi;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;
import com.tmdigital.gestiondestock.dto.OrderSupplierDto;
import com.tmdigital.gestiondestock.model.OrderStatus;
import com.tmdigital.gestiondestock.services.OrderSupplierService;

@RestController
@RequestMapping("/api/v1/ordersuppliers")
public class OrderSupplierController implements OrderSupplierApi {

    private OrderSupplierService orderSupplierService;

    public OrderSupplierController(OrderSupplierService orderSupplierService) {
        this.orderSupplierService = orderSupplierService;
    }

    @Override
    public ResponseEntity<OrderSupplierDto> save(OrderSupplierDto dto) {
        return new ResponseEntity<OrderSupplierDto>(orderSupplierService.save(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderSupplierDto> saveOrderLine(Integer orderId, OrderLineSupplierDto dto) {
        return new ResponseEntity<OrderSupplierDto>(orderSupplierService.addSupplierOrderLine(orderId, dto), HttpStatus.CREATED);
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
    public ResponseEntity<List<OrderLineSupplierDto>> findAllOrderLine(Integer orderId) {
        return ResponseEntity.ok(orderSupplierService.findAllOrderLine(orderId));
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
    public ResponseEntity<Void> updateStatus(Integer orderId, OrderStatus orderStatus) {
        orderSupplierService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateQte(Integer orderId, Integer orderLineId, BigDecimal newQte) {
        orderSupplierService.updateOrderLineQte(orderId, orderLineId, newQte);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateSupplier(Integer orderId, Integer supplierId) {
        orderSupplierService.updateSupplier(orderId, supplierId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateArticle(Integer orderId, Integer orderLineId, Integer newArticleId) {
        orderSupplierService.updateArticle(orderId, orderLineId, newArticleId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void delete(Integer id) {
        orderSupplierService.delete(id);
    }

    @Override
    public ResponseEntity<Void> deleteOrderLine(Integer orderId, Integer orderLineId) {
        orderSupplierService.deleteOrderLine(orderId, orderLineId);
        return ResponseEntity.noContent().build();
    }

}
