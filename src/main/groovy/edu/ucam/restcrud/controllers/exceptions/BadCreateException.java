package edu.ucam.restcrud.controllers.exceptions;

import edu.ucam.restcrud.controllers.exceptions.enums.BadCreateEnum;
import edu.ucam.restcrud.controllers.exceptions.enums.EntityType;

public class BadCreateException extends RuntimeException {
    public BadCreateException(EntityType entity, BadCreateEnum badCreateEnum) {
        super(entity + " no se pudo actualizar. " + badCreateEnum.toString());
    }
}