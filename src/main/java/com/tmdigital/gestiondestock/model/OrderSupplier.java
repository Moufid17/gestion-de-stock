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

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_supplier")
public class OrderSupplier extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "date_commande")
    
    private Instant dateCommande = Instant.now();

    @Column(name = "id_company")
    private Integer idCompany;

    @ManyToOne
    @JoinColumn(name = "id_supplier")
    private Supplier supplier;

    @OneToMany(mappedBy = "orderSupplier")
    private List<OrderLineSupplier> orderLineSupplier;
}
