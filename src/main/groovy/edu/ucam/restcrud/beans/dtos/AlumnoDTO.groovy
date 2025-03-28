package edu.ucam.restcrud.beans.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import edu.ucam.restcrud.database.entities.Alumno
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern

class AlumnoDTO {
    Integer id
    @Pattern(regexp = '^[0-9]{8}[A-Z]$')
    String dni
    String nombreCompleto
    @Past(message = "La fecha de nacimiento no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date fechaNacimiento

    AlumnoDTO(Alumno a) {
        this.id = a.id
        this.dni = a.dni
        this.nombreCompleto = a.nombreCompleto
        this.fechaNacimiento = a.fechaNacimiento
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getDni() {
        return dni
    }

    void setDni(String dni) {
        this.dni = dni
    }

    String getNombreCompleto() {
        return nombreCompleto
    }

    void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto
    }

    Date getFechaNacimiento() {
        return fechaNacimiento
    }

    void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento
    }
}