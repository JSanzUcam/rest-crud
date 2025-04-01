package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Plan

// TODO: AlumnoPlan FULL
class PlanFullDTO extends PlanCursoDTO {
    List<AlumnoDTO> alumnos

    PlanFullDTO(Plan p, boolean correos = false) {
        super(p, short(0))
        this.alumnos = p.alumnoAssoc
            .stream()
            .map(alumno -> {
                if (correos) {
                    return new AlumnoFullDTO(alumno.alumno)
                } else {
                    return new AlumnoDTO(alumno.alumno)
                }
            })
            .collect()
    }

    List<AlumnoDTO> getAlumnos() {
        return alumnos
    }

    void setAlumnos(List<AlumnoDTO> alumnos) {
        this.alumnos = alumnos
    }
}
