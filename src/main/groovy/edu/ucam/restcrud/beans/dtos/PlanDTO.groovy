package edu.ucam.restcrud.beans.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import edu.ucam.restcrud.beans.enums.TipoEstudioEnum
import edu.ucam.restcrud.database.entities.Plan

@JsonIgnoreProperties(ignoreUnknown = true)
class PlanDTO {
    Integer id
    String nombre
    TipoEstudioEnum tipo

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Short borrarEn

    PlanDTO() {
        id = null
        nombre = null
        tipo = null
        borrarEn = null
    }
    PlanDTO(Plan p) {
        this.id = p.id
        this.nombre = p.nombre
        this.tipo = p.tipo
        this.borrarEn = p.borrarEn
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

    Short getBorrarEn() {
        return borrarEn
    }

    void setBorrarEn(Short borrarEn) {
        this.borrarEn = borrarEn
    }
}
