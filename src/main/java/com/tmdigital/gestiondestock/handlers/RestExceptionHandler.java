package com.tmdigital.gestiondestock.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tmdigital.gestiondestock.exception.InvalidEntityException;
import com.tmdigital.gestiondestock.exception.NotFoundEntityException;

/**
 * L'annotation @RestControllerAdvice, ce définie que la classe interceptera toutes les exceptions 
 * lancées par les contrôleurs REST et les traitera avec l'une des méthodes handleException, en 
 * fonction du type d'exception, définit dans <ExceptionHandler>.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundEntityException.class})
    public final ResponseEntity<ErrorDto> handleException(NotFoundEntityException ex, WebRequest req) {

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder()
                .errorCode(ex.getErrorCode())
                .httpCode(notFound.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler({InvalidEntityException.class})
    public final ResponseEntity<ErrorDto> handleException(InvalidEntityException ex, WebRequest req) {

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder()
                .errorCode(ex.getErrorCode())
                .httpCode(badRequest.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDto, badRequest);
    }
}
