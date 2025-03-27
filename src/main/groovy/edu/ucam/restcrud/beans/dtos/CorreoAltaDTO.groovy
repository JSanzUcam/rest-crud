package edu.ucam.restcrud.beans.dtos

import jakarta.validation.constraints.Pattern

class CorreoAltaDTO {
    @Pattern(regexp = '^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$')
    String correo
}
