package com.tmdigital.gestiondestock.model;

import java.io.Serializable;
import java.time.Instant;

// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass // Permet de dire que cette classe est une classe parente. Elle ne sera pas une table dans la base de données.
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    // @CreatedDate
    @Column(name = "createAt", nullable = false, updatable = false)
    @JsonIgnore
    private Instant createAt;

    // @LastModifiedDate
    @Column(name = "updateAt")
    @JsonIgnore
    private Instant updateAt;

    @PrePersist
    void beforeCreate() {
        this.createAt = Instant.now();
    }

    @PreUpdate
    void beforeUpdate() {
        this.updateAt = Instant.now();
    }
}
