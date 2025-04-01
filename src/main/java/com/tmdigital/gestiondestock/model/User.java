package com.tmdigital.gestiondestock.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appuser")
public class User extends AbstractEntity {

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "photo")
    private String photo;

    @Column(name = "phoneTel")
    private String numTel;

    @Embedded
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(table = "roles",name = "roleId", referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Roles> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    @JsonIgnore
    private Company company;
}
