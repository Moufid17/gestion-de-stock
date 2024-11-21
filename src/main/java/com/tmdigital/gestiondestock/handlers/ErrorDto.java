package com.tmdigital.gestiondestock.handlers;

import java.util.ArrayList;
import java.util.List;

import com.tmdigital.gestiondestock.exception.ErrorCodes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDto {

    private Integer httpCode;

    private String message;

    private ErrorCodes errorCode;

    @Builder.Default
    private List<String> errors = new ArrayList<>();
}
