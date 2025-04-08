package edu.ucam.restcrud.controllers.exceptions.enums;

public enum EntityType {
    ALUMNO,
    CORREO,
    PLAN,
    ALUMNO_PLAN,
    OTRO;

    @Override
    public String toString() {
        return switch (this) {
            case ALUMNO -> "Alumno";
            case CORREO -> "Correo";
            case PLAN -> "Plan";
            case ALUMNO_PLAN -> "Alumno-Plan";
            case OTRO -> "Otro";
        };
    }
}
