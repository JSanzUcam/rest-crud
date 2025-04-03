package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.controllers.exceptions.BadCreateException
import edu.ucam.restcrud.controllers.exceptions.BadUpdateException
import edu.ucam.restcrud.controllers.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    void handleGenericEntityNotFoundException() {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadUpdateException.class)
    void handleGenericBadUpdateException() {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCreateException.class)
    void handleGenericBadCreateException() {}
}
