package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Alumno

class AlumnoFullDTO extends AlumnoDTO {
    List<CorreoDTO> correos
    List<PlanCursoDTO> planes

    AlumnoFullDTO(Alumno a) {
        super(a)
        this.correos = a.correos
            .stream()
            .map(correo -> {
                return new CorreoDTO(correo)
            })
            .collect()
        this.planes = a.planAssoc
            .stream()
            .map(plan -> {
                return new PlanCursoDTO(plan.plan, plan.curso)
            })
            .collect()
    }

    List<CorreoDTO> getCorreos() {
        return correos
    }

    void setCorreos(List<CorreoDTO> correos) {
        this.correos = correos
    }

    List<PlanCursoDTO> getPlanes() {
        return planes
    }

    void setPlanes(List<PlanCursoDTO> planes) {
        this.planes = planes
    }
}