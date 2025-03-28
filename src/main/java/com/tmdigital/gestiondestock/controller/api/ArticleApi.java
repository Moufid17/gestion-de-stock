package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.ArticleDto;
import com.tmdigital.gestiondestock.dto.OrderLineClientDto;
import com.tmdigital.gestiondestock.dto.OrderLineSupplierDto;
import com.tmdigital.gestiondestock.dto.SalesLineDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Article", description = "Article API")
public interface ArticleApi {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an article", description = "Allow to create an new article", 
        responses = {
            @ApiResponse(responseCode = "201", description = "Article created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Article already exists", content = @Content),
        }
    )
    ArticleDto save(@RequestBody ArticleDto dto);
   
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive an article", description = "Allow to retreive an article by his id", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive an article with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
        }
    )
    ArticleDto get(@PathVariable Integer id);

    @GetMapping(value="/sales/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive a list of article sales history", description = "Allow to retreive a list of article sales history", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
        }
    )
    ResponseEntity<List<SalesLineDto>> findSalesHistory(@PathVariable Integer articleId);

    @GetMapping(value="/supplier/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive a list of article supplier history", description = "Allow to retreive a list of article supplier history", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
        }
    )
    ResponseEntity<List<OrderLineSupplierDto>> findSupplierOrderHistory(@PathVariable Integer articleId);

    @GetMapping(value="/client/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive a list of article supplier history", description = "Allow to retreive a list of article supplier history", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
        }
    )
    ResponseEntity<List<OrderLineClientDto>> findClientOrderHistory(@PathVariable Integer articleId);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retreive all article", description = "Allow to retreive all articles in the login user company", 
        responses = {
            @ApiResponse(responseCode = "200", description = "Retreive with success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArticleDto[].class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
        }
    )
    List<ArticleDto> getAll();
    
    @DeleteMapping(value="/{id}")
    @Operation(summary = "Delete an article", description = "Allow to delete an article with his id", 
        responses = {
            @ApiResponse(responseCode = "204", description = "Delete with success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
        }
    )
    void delete(@PathVariable Integer id);
}