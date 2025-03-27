package edu.ucam.restcrud.beans.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Pattern

class AlumnoAltaDTO {
    @Pattern(regexp = '^[0-9]{8}[A-Z]$')
    String dni
    String nombre
    @Past(message = "La fecha de nacimiento no puede ser futura")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date fecha
}
