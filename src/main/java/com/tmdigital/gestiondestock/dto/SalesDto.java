package com.tmdigital.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import com.tmdigital.gestiondestock.model.Sales;

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

    public static SalesDto fromEntity (Sales sales) {
        if (sales == null) {
            return null;
        }

        return SalesDto.builder()
            .id(sales.getId())
            .code(sales.getCode())
            .orderDate(sales.getOrderDate())
            .idCompany(sales.getIdCompany())
            .comments(sales.getComments())
            .build();
    }

    public static Sales toEntity (SalesDto salesDto) {
        if (salesDto == null) {
            return null;
        }

        Sales sales = new Sales();
        sales.setId(salesDto.getId());
        sales.setCode(salesDto.getCode());
        sales.setOrderDate(salesDto.getOrderDate());
        sales.setIdCompany(salesDto.getIdCompany());
        sales.setComments(salesDto.getComments());
        return sales;
    }
}
