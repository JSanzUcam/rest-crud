package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.beans.enums.TipoEstudioEnum
import edu.ucam.restcrud.database.entities.Plan

class PlanDTO {
    Integer id
    String nombre
    TipoEstudioEnum tipo

    PlanDTO() {
        this.id = null
        this.nombre = null
        this.tipo = null
    }

    PlanDTO(Plan p) {
        this.id = p.id
        this.nombre = p.nombre
        this.tipo = p.tipo
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getNombre() {
        return nombre
    }

    void setNombre(String nombre) {
        this.nombre = nombre
    }

    TipoEstudioEnum getTipo() {
        return tipo
    }

    void setTipo(TipoEstudioEnum tipo) {
        this.tipo = tipo
    }
}
