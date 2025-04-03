package edu.ucam.restcrud.controllers.exceptions.enums;

public enum BadCreateEnum {
    /// La ID de la Entidad no se ha especificado durante un Create
    /// Si estabas intentando actualizarla, usa Update
    ENTITY_ID_SPECIFIED,
    /// La ID de la relación se ha especificado durante un Create
    /// Si estabas intentando actualizarla, usa Update.
    /// Para Join Tables.
    RELATIONSHIP_ID_SPECIFIED;

    @Override
    public String toString() {
        return switch (this) {
            case ENTITY_ID_SPECIFIED -> "ID fue especificada durante un Create";
            case RELATIONSHIP_ID_SPECIFIED -> "ID de la relación se especificó durante un Create O no se especificó la ID de una Entidad";
        };
    }
}
