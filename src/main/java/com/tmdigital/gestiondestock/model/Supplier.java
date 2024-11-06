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

@Data // Génère les méthodes getter, setter, toString, equals, et hashCode
@EqualsAndHashCode(callSuper = true) // Inclut les champs de la classe parente dans equals et hashCode
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Entity
@Table(name = "supplier")
public class Supplier extends AbstractEntity {

    @Column(name = "fistname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = true)    
    private String lastname;
    
    @Column(name = "email", nullable = false)    
    private String email;
    
    @Column(name = "phone_number", nullable = false)    
    private String phonenumber;
    
    @Embedded // Permet d'indiquer que l'objet est un objet imbriqué (composé de plusieurs champs)
    @Column(name = "address", nullable = true)    
    private Address address;
    
    @Column(name = "photo", nullable = true)    
    private String photo;

    @OneToMany(mappedBy = "supplier")
    private List<OrderSupplier> ordersSupplier;
}
