package com.tmdigital.gestiondestock.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    CANCELED(0),
    IN_PROGRESS(1),
    VALIDATED(2);

    private final int code;

    public int getCode() {
        return code;
    }
}
