package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesDto {

    private Integer id;
    
    private String code;

    private Instant orderDate;

    private Integer idCompany;

    private String comments;

    private List<SalesLineDto> salesLines;
}
