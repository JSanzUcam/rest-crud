package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.beans.enums.TipoPersonaEnum

class PlanAltaDTO {
    String nombre
    TipoPersonaEnum tipo

    String getNombre() {
        return nombre
    }

    void setNombre(String nombre) {
        this.nombre = nombre
    }

    TipoPersonaEnum getTipo() {
        return tipo
    }

    void setTipo(TipoPersonaEnum tipo) {
        this.tipo = tipo
    }
}
