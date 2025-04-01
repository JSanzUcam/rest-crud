package edu.ucam.restcrud.beans.dtos

import jakarta.validation.constraints.NotNull

class AlumnoPlanAltaDTO {
    @NotNull
    Integer alumno_id
    @NotNull
    Integer plan_id
    @NotNull
    Short curso

    Integer getAlumno_id() {
        return alumno_id
    }

    void setAlumno_id(Integer alumno_id) {
        this.alumno_id = alumno_id
    }

    Integer getPlan_id() {
        return plan_id
    }

    void setPlan_id(Integer plan_id) {
        this.plan_id = plan_id
    }

    Short getCurso() {
        return curso
    }

    void setCurso(Short curso) {
        this.curso = curso
    }
}
