package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Alumno

class AlumnoConPlanesDTO extends AlumnoDTO {
    List<PlanDTO> planes

    AlumnoConPlanesDTO(Alumno a) {
        super(a)
        planes = a.planAssoc.plan.collect(plan -> {
            return new PlanDTO(plan)
        })
    }
}
