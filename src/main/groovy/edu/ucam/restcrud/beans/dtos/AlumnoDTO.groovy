package edu.ucam.restcrud.beans.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import edu.ucam.restcrud.beans.dtos.structs.PlanCurso
import edu.ucam.restcrud.beans.enums.TipoDocumentoEnum
import edu.ucam.restcrud.beans.validators.DocumentFormat
import edu.ucam.restcrud.database.universidad.entities.Alumno
import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.validation.constraints.Past

import java.time.Instant

@DocumentFormat // Validador DNI/NIE/Pasaporte
@JsonIgnoreProperties(ignoreUnknown = true)
class AlumnoDTO {
    Integer id

    @NotNull
    TipoDocumentoEnum tipoDocumento
    @NotNull
    String numeroDocumento
    @NotNull
    String nombreCompleto

    // Solo se muestra cuando pedimos que se muestre todo desde el constructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<CorreoDTO> correos
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<PlanCurso> planes

    @Past(message = "La fecha de nacimiento no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date fechaNacimiento

    AlumnoDTO() {
        id = null
        tipoDocumento = TipoDocumentoEnum.DNI
        numeroDocumento = "00000000A"
        nombreCompleto = "default"
        fechaNacimiento = Date.from(Instant.EPOCH)

        correos = null
        planes = null
    }
    AlumnoDTO(Alumno a, boolean completo = false) {
        this.id = a.id
        this.tipoDocumento = a.tipoDocumento
        this.numeroDocumento = a.numeroDocumento
        this.nombreCompleto = a.nombreCompleto
        this.fechaNacimiento = a.fechaNacimiento

        if (completo) {
            this.correos = a.correos
                .stream()
                .map(correo -> {
                    return new CorreoDTO(correo)
                })
                .collect()
            this.planes = a.planAssoc
                .stream()
                .map(plan -> {
                    def pc = new PlanCurso()
                    pc.id = plan.id
                    pc.plan = new PlanDTO(plan.plan)
                    pc.curso = plan.curso
                    pc
                })
                .collect()
        } else {
            this.correos = null
            this.planes = null
        }
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
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

    String getNombreCompleto() {
        return nombreCompleto
    }

    void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto
    }

    List<CorreoDTO> getCorreos() {
        return correos
    }

    void setCorreos(List<CorreoDTO> correos) {
        this.correos = correos
    }

    Date getFechaNacimiento() {
        return fechaNacimiento
    }

    void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento
    }
}