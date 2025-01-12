package com.tmdigital.gestiondestock.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MovementSource {
    INVENTORY_EXIT, INVENTORY_ENTRY, ORDER_SUPPLIER, ORDER_CLIENT;
}
