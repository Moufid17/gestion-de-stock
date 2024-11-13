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
import lombok.Builder;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_client")
public class OrderClient extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "date_commande")
    @Builder.Default
    private Instant dateCommande = Instant.now();

    @Column(name = "id_company")
    private Integer idCompany;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToMany(mappedBy = "orderClient")
    private List<OrderLineClient> orderLineClients;

}
