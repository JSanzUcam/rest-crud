package edu.ucam.restcrud.controllers.exceptions.enums;

public enum BadUpdateEnum {
    /// La ID de la Entidad no se ha especificado durante un Update
    ENTITY_ID_NOT_SPECIFIED,
    /// La ID de la relación no se ha especificado durante un Update
    /// Para Join Tables
    RELATIONSHIP_ID_NOT_SPECIFIED;

    @Override
    public String toString() {
        return switch (this) {
            case ENTITY_ID_NOT_SPECIFIED -> "ID no se especificó durante un Update";
            case RELATIONSHIP_ID_NOT_SPECIFIED -> "ID de la relación no se especificó durante un Update O se especificó una ID de Entidad ilegal";
        };
    }
}
