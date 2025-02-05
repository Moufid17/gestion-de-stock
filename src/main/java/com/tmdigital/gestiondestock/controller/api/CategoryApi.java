package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category", description = "Category API")
public interface CategoryApi {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an category", description = "Allow to create an new category", 
        responses = {
            @ApiResponse(responseCode = "201", description = "Category created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Category already exists", content = @Content),
        }
    )
    CategoryDto save(@RequestBody CategoryDto dto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive an Category", description = "Allow to retreive an Category by his id", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive an Category with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
        }
    )
    CategoryDto findById(@PathVariable Integer id);

    @GetMapping(value = "/filter/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive an Category by code", description = "Allow to retreive an Category by his code", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive an Category with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
        }
    )
    CategoryDto findByCode(@PathVariable String code);

    // [ ] Admin and company Admin route
    @GetMapping(value = "/filter/company/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all Category by company id", description = "Allow to retreive all Categories in the login user company", 
    responses = {
        @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto[].class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
    }
    )
    List<CategoryDto> findAllByCompany(@PathVariable("id") Integer idCompany);
    
    // [ ] Admin route
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all Category", description = "Allow to retreive all Categories in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
        }
    )
    List<CategoryDto> findAll();

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete an Category", description = "Allow to delete an Category by with id", 
        responses = {
            @ApiResponse(responseCode = "204", description = "Delete with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
        }
    )
    void delete(@PathVariable Integer id);
}
