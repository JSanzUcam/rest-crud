package edu.ucam.restcrud.beans.dtos

import jakarta.validation.constraints.NotNull

class AlumnoPlanAltaDTO {
    Integer id

    Integer alumno_id
    @NotNull
    Integer plan_id
    @NotNull
    Short curso

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

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
