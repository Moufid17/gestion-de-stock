package com.tmdigital.gestiondestock.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    CANCELED,
    IN_PROGRESS,
    DELIVERED;
}
