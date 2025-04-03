package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.controllers.exceptions.BadCreateException
import edu.ucam.restcrud.controllers.exceptions.BadUpdateException
import edu.ucam.restcrud.controllers.exceptions.InvalidArgumentsException
import edu.ucam.restcrud.controllers.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<String> handleEntityNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())
    }

    @ExceptionHandler(BadUpdateException.class)
    ResponseEntity<String> handleBadUpdateException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())
    }

    @ExceptionHandler(BadCreateException.class)
    ResponseEntity<String> handleBadCreateException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())
    }

    @ExceptionHandler(InvalidArgumentsException.class)
    ResponseEntity<String> handleInvalidArgumentsException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())
    }
}
