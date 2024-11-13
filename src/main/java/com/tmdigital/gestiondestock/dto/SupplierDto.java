package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.OrderSupplier;
import com.tmdigital.gestiondestock.model.Supplier;

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
    
    @JsonIgnore
    private List<OrderSupplier> ordersSupplier;

    public static SupplierDto fromEntity (Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        return SupplierDto.builder()
            .id(supplier.getId())
            .firstname(supplier.getFirstname())
            .lastname(supplier.getLastname())
            .email(supplier.getEmail())
            .phonenumber(supplier.getPhonenumber())
            .address(AddressDto.fromEntity(supplier.getAddress()))
            .photo(supplier.getPhoto())
            .idCompany(supplier.getIdCompany())
            .build();
    }

    public static Supplier toEntity (SupplierDto supplierDto) {
        if (supplierDto == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setId(supplierDto.getId());
        supplier.setFirstname(supplierDto.getFirstname());
        supplier.setLastname(supplierDto.getLastname());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setPhonenumber(supplierDto.getPhonenumber());
        supplier.setAddress(AddressDto.toEntity(supplierDto.getAddress()));
        supplier.setPhoto(supplierDto.getPhoto());
        supplier.setIdCompany(supplierDto.getIdCompany());
        return supplier;
    }
}
