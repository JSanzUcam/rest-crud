package edu.ucam.restcrud.controllers.exceptions;

import edu.ucam.restcrud.controllers.exceptions.enums.BadUpdateEnum;
import edu.ucam.restcrud.controllers.exceptions.enums.EntityType;

public class BadUpdateException extends RuntimeException {
    public BadUpdateException(EntityType entity, BadUpdateEnum badUpdateEnum) {
        super(entity + " no se pudo actualizar. " + badUpdateEnum.toString());
    }
}
