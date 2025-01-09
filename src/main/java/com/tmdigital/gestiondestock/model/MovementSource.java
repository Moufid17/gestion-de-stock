package com.tmdigital.gestiondestock.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MovementSource {

    INVENTORY_EXIT(-1),
    INVENTORY_ENTRY(1),
    ORDER_SUPPLIER(2),
    ORDER_CLIENT(3);

    private final int code;

    public int getValue() {
        return code;
    }
}
