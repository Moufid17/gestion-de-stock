package com.tmdigital.gestiondestock.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // Permet d'indiquer que l'objet est un objet imbriqué (composé de plusieurs champs), donc pas besoin d'un Id pour l'identification
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country")
    private String country;
}
