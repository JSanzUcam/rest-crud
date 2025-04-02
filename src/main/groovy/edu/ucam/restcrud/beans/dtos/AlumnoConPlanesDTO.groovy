package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Alumno

class PlanCurso {
    PlanDTO plan
    Short curso
}

class AlumnoConPlanesDTO extends AlumnoDTO {
    List<PlanDTO> planes


    AlumnoConPlanesDTO(Alumno a) {
        super(a)
        planes = a.planAssoc.stream().map(plan -> {
            def pc = new PlanCurso()
            pc.plan = new PlanDTO(plan.plan)
            pc.curso = plan.curso
            pc
        }).collect()
    }
}
