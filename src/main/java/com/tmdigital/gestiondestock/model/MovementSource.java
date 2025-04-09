package com.tmdigital.gestiondestock.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MovementSource {
    SALES, ORDER_SUPPLIER, ORDER_CLIENT;
}
