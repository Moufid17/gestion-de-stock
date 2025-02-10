package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.OrderSupplierDto;

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
    OrderSupplierDto save(@RequestBody OrderSupplierDto dto);

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
}
