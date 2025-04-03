package edu.ucam.restcrud.controllers.exceptions;

import edu.ucam.restcrud.controllers.exceptions.enums.EntityType;

public class NotFoundException extends RuntimeException {
    public NotFoundException(EntityType entity, Integer id) {
        super(entity + " con ID " + id + " no encontrado.");
    }
}
