package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Alumno

class AlumnoDTO {
    Integer id
    String dni
    String nombreCompleto
    Date fechaNacimiento

    AlumnoDTO(Alumno a) {
        this.id = a.id
        this.dni = a.dni
        this.nombreCompleto = a.nombreCompleto
        this.fechaNacimiento = a.fechaNacimiento
    }
}