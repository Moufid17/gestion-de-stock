package com.tmdigital.gestiondestock.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



import lombok.Builder;

@Data // Génère les méthodes getter, setter, toString, equals, et hashCode
@Builder 
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company") 
public class Company extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String mail;

    @Column(name = "numTel")
    private String numTel;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "photo")
    private String photo;

    @Column(name = "website")
    private String website;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<User> users;
}
