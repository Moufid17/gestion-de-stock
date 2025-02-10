package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.CompanyDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Company", description = "The Company API")
public interface CompanyApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an company", description = "Allow to create an new company", 
        responses = {
            @ApiResponse(responseCode = "201", description = "Company created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Company already exists", content = @Content),
        }
    )
    CompanyDto save(@RequestBody CompanyDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive an company", description = "Allow to retreive an company by his id", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive an Company with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
        }
    )
    CompanyDto findById(@PathVariable Integer id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all Company", description = "Allow to retreive all Categories in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
        }
    )
    List<CompanyDto> findAll();

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an company", description = "Allow to delete an company with his id", 
        responses = {
            @ApiResponse(responseCode = "204", description = "Delete with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
        }
    )
    void delete(@PathVariable Integer id);
}
