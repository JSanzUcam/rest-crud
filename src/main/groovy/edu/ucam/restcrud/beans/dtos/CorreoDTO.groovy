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
}
