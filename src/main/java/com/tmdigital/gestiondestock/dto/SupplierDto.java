package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.tmdigital.gestiondestock.model.OrderSupplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {

    private Integer id;
    
    private String firstname;

    private String lastname;
    
    private String email;
    
    private String phonenumber;
    
    private AddressDto address;
    
    private String photo;

    private Integer idCompany;
    
    private List<OrderSupplier> ordersSupplier;
}