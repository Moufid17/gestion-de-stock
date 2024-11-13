package com.tmdigital.gestiondestock.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CategoryDto {

    private Integer id;
    
    private String code;

    private String designation;

    private Integer company;

    private List<ArticleDto> articles;
}
