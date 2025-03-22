package com.tmdigital.gestiondestock.controller.api;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;
import com.tmdigital.gestiondestock.model.OrderStatus;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "OrderSupplier", description = "The OrderSupplier API")
public interface OrderSupplierApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)  
    @Operation(summary = "Create an orderSupplier", description = "Allow to create an new orderSupplier", 
        responses = {
            @ApiResponse(responseCode = "201", description = "OrderSupplier created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderSupplierDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "OrderSupplier already exists", content = @Content),
        }
    ) 
    ResponseEntity<OrderSupplierDto> save(@RequestBody OrderSupplierDto dto);

    @PostMapping(value="/article/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new supplier order line", description = "Allow to create a new supplier order line", 
        responses = {
            @ApiResponse(responseCode = "200", description = "New supplier order Line created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderLineSupplierDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order already exists", content = @Content),
        }
    )  
    ResponseEntity<OrderSupplierDto> saveOrderLine(@PathVariable Integer orderId, @RequestBody OrderLineSupplierDto dto);

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive an orderSupplier", description = "Allow to retreive an orderSupplier by his id", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive an orderSupplier with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderSupplier not found"),
        }
    )
    OrderSupplierDto findById(@PathVariable Integer id);

    @GetMapping(value="/filter/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderSupplier by company id", description = "Allow to retreive all orderSupplier in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderSupplierDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "orderSupplier not found"),
        }
    )
    OrderSupplierDto findByCode(@PathVariable String code);

    @GetMapping(value = "/{orderId}/orderlines", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderSupplier by company id", description = "Allow to retreive all orderSupplier in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderLineSupplierDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
        }
    )
    ResponseEntity<List<OrderLineSupplierDto>> findAllOrderLine(@PathVariable Integer orderId);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderSupplier", description = "Allow to retreive all orderSupplier in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderSupplierDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderSupplier not found"),
        }
    )
    List<OrderSupplierDto> findAll();

    @GetMapping(value="/filter/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderSupplier by company id", description = "Allow to retreive all orderSupplier in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderSupplierDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderSupplier not found"),
        }
    )
    List<OrderSupplierDto> findAllByCompany(@PathVariable Integer id);

    @GetMapping(value="/filter/supplier/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderSupplier by supplier id", description = "Allow to retreive all orderSupplier by supplier id", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderSupplierDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderSupplier not found"),
        }
    )
    List<OrderSupplierDto> findAllBySupplier(@PathVariable Integer id);

    @PatchMapping(value = "/update/status/{orderId}/{orderStatus}")
    @Operation(summary = "Update a spplier order status", description = "Allow to update a spplier order status", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Order update"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Order already exists"),
        }
    )  
    ResponseEntity<Void> updateStatus(@PathVariable Integer orderId, @PathVariable OrderStatus orderStatus);

    @PatchMapping(value = "/update/quantity/{orderId}/{orderLineId}/{newQte}")
    @Operation(summary = "Update an order quantity", description = "Allow to update a order quantity", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Order update quantity"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Order already exists"),
        }
    )  
    ResponseEntity<Void> updateQte(@PathVariable Integer orderId, @PathVariable Integer orderLineId, @PathVariable BigDecimal newQte);

    @PatchMapping(value = "/update/supplier/{orderId}/{supplierId}")
    @Operation(summary = "Update an order with a new supplier", description = "Allow to update an order with a new supplier", 
        responses = {
            @ApiResponse(responseCode = "200", description = "update an order supplier"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
        }
    )
    ResponseEntity<Void> updateSupplier(@PathVariable Integer orderId, @PathVariable Integer supplierId);

    @PatchMapping(value = "/update/article/{orderId}/{orderLineId}/{articleId}")
    @Operation(summary = "Update an order article", description = "Allow to update a order article", 
        responses = {
            @ApiResponse(responseCode = "200", description = "OrderClient update article"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Order already exists"),
        }
    )  
    ResponseEntity<Void> updateArticle(@PathVariable Integer orderId, @PathVariable Integer orderLineId, @PathVariable("articleId") Integer newArticleId);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an orderSupplier", description = "Allow to delete an orderSupplier with his id", 
        responses = {
            @ApiResponse(responseCode = "204", description = "Delete with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderSupplier not found"),
        }
    )
    void delete(@PathVariable Integer id);

    @DeleteMapping("/delete/article/{orderId}/{orderLineId}")
    @Operation(summary = "Delete an orderLine", description = "Allow to delete an orderLine with his id", 
        responses = {
            @ApiResponse(responseCode = "204", description = "Delete with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderClient not found"),
        }
    )
    ResponseEntity<Void> deleteOrderLine(@PathVariable Integer orderId, @PathVariable Integer orderLineId);
}
