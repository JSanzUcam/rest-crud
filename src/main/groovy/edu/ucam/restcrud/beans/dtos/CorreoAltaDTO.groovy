package edu.ucam.restcrud.beans.dtos

import jakarta.validation.constraints.Pattern

class CorreoAltaDTO {
    @Pattern(regexp = '^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$')
    String correo

    String getCorreo() {
        return correo
    }

    void setCorreo(String correo) {
        this.correo = correo
    }
}
