package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Plan

class PlanConAlumnosDTO extends PlanDTO {
    List<AlumnoDTO> alumnos

    PlanConAlumnosDTO(Plan p) {
        super(p)
        this.alumnos = p.alumnoAssoc.alumno.collect(alumno -> {
            return new AlumnoDTO(alumno)
        })
    }
}
