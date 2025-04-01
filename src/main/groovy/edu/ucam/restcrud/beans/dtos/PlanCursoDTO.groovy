package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Plan
import jakarta.validation.constraints.NotNull

// TODO: AlumnoPlan
class PlanCursoDTO extends PlanDTO {
    @NotNull
    short curso

    PlanCursoDTO() {
        super()
        curso = 0
    }
    PlanCursoDTO(Plan p, short curso) {
        super(p)
        this.curso = curso
    }

    short getCurso() {
        return curso
    }

    void setCurso(short curso) {
        this.curso = curso
    }
}
