package com.fpmislata.basespring.common.errorHandler;

import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
// Anotación que permite definir un controlador de excepciones que se aplicara a todos los controladores.
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND) // Devuelve codigo 404 cuando no se encuentra el recurso.
    @ExceptionHandler({
            ResourceNotFoundException.class
    })
    @ResponseBody
    public ErrorMessage notFoundRequest(ResourceNotFoundException exception) {
        return new ErrorMessage(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Devuelve codigo 500 cuando se produce un error, es generico.
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorMessage handleGeneralException(Exception exception) {
        return new ErrorMessage(exception);
    }
}
