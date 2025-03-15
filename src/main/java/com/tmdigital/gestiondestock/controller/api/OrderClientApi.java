package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.OrderClientDto;
import com.tmdigital.gestiondestock.model.OrderStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "OrderClient", description = "The OrderClient API")
public interface OrderClientApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an orderClient", description = "Allow to create an new orderClient", 
        responses = {
            @ApiResponse(responseCode = "201", description = "OrderClient created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderClientDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "OrderClient already exists", content = @Content),
        }
    )  
    ResponseEntity<OrderClientDto> save(@RequestBody OrderClientDto dto);

    @PatchMapping(value = "/update/{id}/{orderStatus}", produces = MediaType.APPLICATION_JSON_VALUE )
    @Operation(summary = "Update an orderClient status", description = "Allow to update an new orderClient", 
        responses = {
            @ApiResponse(responseCode = "200", description = "OrderClient update"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "OrderClient already exists"),
        }
    )  
    ResponseEntity<OrderClientDto> updateStatus(@PathVariable Integer id, @PathVariable OrderStatus orderStatus);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive an OrderClient", description = "Allow to retreive an OrderClient by his id", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive an OrderClient with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderClient not found"),
        }
    )
    ResponseEntity<OrderClientDto> findById(@PathVariable Integer id);
    
    @GetMapping(value = "/filter/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all OrderClient by company id", description = "Allow to retreive all orderClient in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderClientDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderClient not found"),
        }
    )
    ResponseEntity<OrderClientDto> findByCode(@PathVariable String code);

    @GetMapping(value = "/filter/client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderClient by company id", description = "Allow to retreive all orderClient in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderClientDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderClient not found"),
        }
    )
    ResponseEntity<List<OrderClientDto>> findAllByClientId(@PathVariable Integer id);

    @GetMapping(value = "/filter/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderClient by company id", description = "Allow to retreive all orderClient in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderClientDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderClient not found"),
        }
    )
    ResponseEntity<List<OrderClientDto>> findAllByCompany(@PathVariable Integer id);
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all orderClient", description = "Allow to retreive all orderSupplier in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderClientDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderClient not found"),
        }
    )
    ResponseEntity<List<OrderClientDto>> findAll();

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an orderClient", description = "Allow to delete an orderClient with his id", 
        responses = {
            @ApiResponse(responseCode = "204", description = "Delete with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "OrderClient not found"),
        }
    )
    void delete(@PathVariable Integer id);
}
