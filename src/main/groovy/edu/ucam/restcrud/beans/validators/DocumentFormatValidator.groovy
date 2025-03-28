package edu.ucam.restcrud.beans.validators

import edu.ucam.restcrud.beans.enums.TipoDocumentoEnum
import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class DocumentFormatValidator implements ConstraintValidator<DocumentFormat, AlumnoDTO> {
    @Override
    void initialize(DocumentFormat constraintAnnotation) {
        super.initialize(constraintAnnotation)
    }

    @Override
    boolean isValid(AlumnoDTO alumno, ConstraintValidatorContext context) {
        switch (alumno.tipoDocumento) {
            case TipoDocumentoEnum.DNI:
                return alumno.numeroDocumento.matches('^[0-9]{8}-?[A-Z]$')
            case TipoDocumentoEnum.NIE:
                return alumno.numeroDocumento.matches('^[XYZ]-?[0-9]{7}-?[A-Z]$')
            case TipoDocumentoEnum.Pasaporte:
                // Mas de 7 caracteres alfanumericos
                return alumno.numeroDocumento.matches('^[A-Za-z0-9]{7,}$')
        }
        // Esto no deberia pasar nunca pero soluciona avisos
        return false
    }
}
