package edu.ucam.restcrud.beans.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Constraint(validatedBy = DocumentFormatValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface DocumentFormat {
    String message() default "Numero de documento invalido para este tipo de documento"
    Class<?>[] groups() default []
    Class<? extends Payload>[] payload() default []
}