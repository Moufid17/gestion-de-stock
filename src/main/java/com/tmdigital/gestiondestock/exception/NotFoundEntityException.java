package com.tmdigital.gestiondestock.exception;

import lombok.Getter;

public class NotFoundEntityException extends RuntimeException {

    @Getter
    private ErrorCodes errorCode;

    public NotFoundEntityException(String message) {
        super(message);
    }

    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundEntityException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotFoundEntityException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
