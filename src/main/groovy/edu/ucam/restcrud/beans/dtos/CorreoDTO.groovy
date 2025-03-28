package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Correo

class CorreoDTO {
    Integer id
    String correo
    Date fechaAlta
    Date fechaModificacion

    CorreoDTO(Correo c) {
        this.id = c.id
        this.correo = c.correo
        this.fechaAlta = c.fechaAlta
        this.fechaModificacion = c.fechaModificacion
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getCorreo() {
        return correo
    }

    void setCorreo(String correo) {
        this.correo = correo
    }

    Date getFechaAlta() {
        return fechaAlta
    }

    void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta
    }

    Date getFechaModificacion() {
        return fechaModificacion
    }

    void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion
    }
}
