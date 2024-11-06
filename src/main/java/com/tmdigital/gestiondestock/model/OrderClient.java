package com.tmdigital.gestiondestock.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "order_client")
public class OrderClient extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "date_commande")
    private Instant dateCommande = Instant.now();

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToMany(mappedBy = "orderClient")
    private List<OrderLineClient> orderLineClients;

}
