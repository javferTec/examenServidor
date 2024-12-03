package com.fpmislata.basespring.common.errorHandler;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorMessage { // Clase que se encarga de devolver un mensaje de error.
    private final String error;
    private final String message;

    public ErrorMessage(Exception exception) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();

    }
}