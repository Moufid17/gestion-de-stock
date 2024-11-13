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

@Data
@Builder 
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {

    @Column(name = "fistname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = true)    
    private String lastname;
    
    @Column(name = "email", nullable = false)    
    private String email;
    
    @Column(name = "phone_number", nullable = false)    
    private String phonenumber;
    
    @Embedded // Permet d'indiquer que l'objet est un objet imbriqué (composé de plusieurs champs)
    private Address address;
    
    @Column(name = "photo")    
    private String photo;

    @Column(name = "id_company", nullable = false)
    private Integer idCompany;

    @OneToMany(mappedBy = "client")
    private List<OrderClient> ordersClient;
}
