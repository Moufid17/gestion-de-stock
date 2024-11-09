package com.tmdigital.gestiondestock.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
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
@Table(name = "sales")
public class Sales extends AbstractEntity {
    
    @Column(name = "code")
    private String code;

    @Column(name = "order_date")
    @Builder.Default
    private Instant orderDate = Instant.now();

    @Column(name = "id_company")
    private Integer idCompany;

    @Column(name = "comments")
    private String comments;

    @OneToMany(mappedBy = "sales")
    private List<SalesLine> salesLines;

}
