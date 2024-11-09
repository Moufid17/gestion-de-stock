package com.tmdigital.gestiondestock.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StockMovementType {
    INPUT(1), OUTPUT(2);

    private final int value;

    public int getValue() {
        return value;
    }
}
