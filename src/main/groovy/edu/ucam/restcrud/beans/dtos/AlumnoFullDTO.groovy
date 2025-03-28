package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Alumno

class AlumnoFullDTO extends AlumnoDTO {
    List<CorreoDTO> correos
    List<PlanDTO> planes

    AlumnoFullDTO(Alumno a) {
        super(a)
        this.correos = a.correos
            .stream()
            .map(correo -> {
                return new CorreoDTO(correo)
            })
            .collect()
        this.planes = a.planes
            .stream()
            .map(plan -> {
                return new PlanDTO(plan)
            })
            .collect()
    }

    List<CorreoDTO> getCorreos() {
        return correos
    }

    void setCorreos(List<CorreoDTO> correos) {
        this.correos = correos
    }

    List<PlanDTO> getPlanes() {
        return planes
    }

    void setPlanes(List<PlanDTO> planes) {
        this.planes = planes
    }
}