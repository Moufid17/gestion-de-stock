package com.tmdigital.gestiondestock.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StockMovementType {
    INPUT, OUTPUT, POS_UPDATE, NEG_UPDATE;
}
