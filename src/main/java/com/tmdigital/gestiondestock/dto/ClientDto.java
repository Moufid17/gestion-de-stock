package com.tmdigital.gestiondestock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tmdigital.gestiondestock.model.Client;

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

    public static ClientDto fromEntity (Client client) {
        if (client == null) return null;

        return ClientDto.builder()
            .id(client.getId())
            .firstname(client.getFirstname())
            .lastname(client.getLastname())
            .email(client.getEmail())
            .phonenumber(client.getPhonenumber())
            .address(AddressDto.fromEntity(client.getAddress()))
            .photo(client.getPhoto())
            .idCompany(client.getIdCompany())
            .build();
    }

    public static Client toEntity (ClientDto clientDto) {
        if (clientDto == null) return null;

        Client client = new Client();
        client.setId(clientDto.getId());
        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setPhonenumber(clientDto.getPhonenumber());
        client.setAddress(AddressDto.toEntity(clientDto.getAddress()));
        client.setPhoto(clientDto.getPhoto());
        client.setIdCompany(clientDto.getIdCompany());

        return client;
    }

}
