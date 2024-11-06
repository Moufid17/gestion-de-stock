package com.tmdigital.gestiondestock.model;

import java.util.List;

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
@Table(name = "category") 

public class Category extends AbstractEntity {
    private String code;
    private String designation;

    @OneToMany(mappedBy = "category") // One category can have many articles. mappedBy is used to specify the field in the Article class that owns the relationship.
    private List<Article> articles;
}
