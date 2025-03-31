package edu.ucam.restcrud.database.embeddable

import jakarta.persistence.Embeddable

@Embeddable
class AlumnoPlanId implements Serializable {
    private Integer alumno
    private Integer plan

    Integer getAlumno() {
        return alumno
    }

    void setAlumno(Integer alumnoId) {
        this.alumno = alumnoId
    }

    Integer getPlan() {
        return plan
    }

    void setPlan(Integer planId) {
        this.plan = planId
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        AlumnoPlanId that = (AlumnoPlanId) o

        if (alumno != that.alumno) return false
        if (plan != that.plan) return false

        return true
    }

    int hashCode() {
        int result
        result = (alumno != null ? alumno.hashCode() : 0)
        result = 31 * result + (plan != null ? plan.hashCode() : 0)
        return result
    }
}
