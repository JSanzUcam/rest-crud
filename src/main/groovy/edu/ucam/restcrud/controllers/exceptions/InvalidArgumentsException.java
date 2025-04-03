package edu.ucam.restcrud.controllers.exceptions;

import edu.ucam.restcrud.controllers.exceptions.enums.EntityType;

public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException(EntityType entityType, Object expectedValue, Object actualValue) {
        super(entityType + " tiene argumentos inválidos. Se esperaba " + expectedValue + " pero se obtuvo " + actualValue);
    }
}
