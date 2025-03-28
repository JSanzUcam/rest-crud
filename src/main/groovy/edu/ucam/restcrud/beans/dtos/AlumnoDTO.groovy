package edu.ucam.restcrud.beans.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import edu.ucam.restcrud.beans.enums.TipoDocumentoEnum
import edu.ucam.restcrud.beans.validators.DocumentFormat
import edu.ucam.restcrud.database.entities.Alumno
import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.validation.constraints.Past

import java.time.Instant

@DocumentFormat // Validador DNI/NIE/Pasaporte
class AlumnoDTO {
    Integer id

    @NotNull
    TipoDocumentoEnum tipoDocumento
    @NotNull
    String numeroDocumento

    @NotNull
    String nombreCompleto

    @Past(message = "La fecha de nacimiento no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date fechaNacimiento

    AlumnoDTO() {
        id = null
        tipoDocumento = TipoDocumentoEnum.DNI
        numeroDocumento = "00000000A"
        nombreCompleto = "default"
        fechaNacimiento = Date.from(Instant.EPOCH)
    }
    AlumnoDTO(Alumno a) {
        this.id = a.id
        this.tipoDocumento = a.tipoDocumento
        this.numeroDocumento = a.numeroDocumento
        this.nombreCompleto = a.nombreCompleto
        this.fechaNacimiento = a.fechaNacimiento
    }

    TipoDocumentoEnum getTipoDocumento() {
        return tipoDocumento
    }

    void setTipoDocumento(TipoDocumentoEnum tipoDocumento) {
        this.tipoDocumento = tipoDocumento
    }

    String getNumeroDocumento() {
        return numeroDocumento
    }

    void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
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