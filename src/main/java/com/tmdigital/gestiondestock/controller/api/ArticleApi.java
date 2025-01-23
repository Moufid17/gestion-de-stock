package com.tmdigital.gestiondestock.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tmdigital.gestiondestock.dto.ArticleDto;
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
            @ApiResponse(responseCode = "409", description = "Article already exists", content = @Content)
        }
    )
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> getAll();

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto get(@PathVariable Integer id);
    
    @DeleteMapping(value="/{id}")
    void delete(@PathVariable Integer id);
}
