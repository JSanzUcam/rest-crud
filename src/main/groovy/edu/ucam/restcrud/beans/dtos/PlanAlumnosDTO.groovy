package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Plan

class PlanAlumnosDTO extends PlanDTO {
    List<AlumnoDTO> alumnos

    PlanAlumnosDTO(Plan p, boolean correos = false) {
        super(p)
        this.alumnos = p.alumnos
            .stream()
            .map(alumno -> {
                if (correos) {
                    return new AlumnoFullDTO(alumno)
                } else {
                    return new AlumnoDTO(alumno)
                }
            })
            .collect()
    }
}
