package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.beans.enums.TipoPersonaEnum
import edu.ucam.restcrud.database.entities.Plan

class PlanDTO {
    Integer id
    String nombre
    TipoPersonaEnum tipo

    PlanDTO(Plan p) {
        this.id = p.id
        this.nombre = p.nombre
        this.tipo = p.tipo
    }
}
