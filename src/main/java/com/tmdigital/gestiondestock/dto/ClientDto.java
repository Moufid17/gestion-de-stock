package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ClientDto {

    private Integer id;
    
    private String firstname;

    private String lastname;
    
    private String email;
    
    private String phonenumber;
    
    private AddressDto address;
    
    private String photo;

    private Integer idCompany;

    @JsonIgnore
    private List<OrderClientDto> ordersClient;
}
