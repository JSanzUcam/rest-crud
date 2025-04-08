package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.beans.dtos.structs.PlanCurso
import edu.ucam.restcrud.database.universidad.entities.Alumno

class AlumnoConPlanesDTO extends AlumnoDTO {
    List<PlanCurso> planes

    AlumnoConPlanesDTO(Alumno a) {
        super(a)
        planes = a.planAssoc.stream().map(plan -> {
            def pc = new PlanCurso()
            pc.id = plan.id
            pc.plan = new PlanDTO(plan.plan)
            pc.curso = plan.curso
            pc
        }).collect()
    }
}
