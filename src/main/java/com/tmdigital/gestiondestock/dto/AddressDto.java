package com.tmdigital.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private Integer id;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;
}
