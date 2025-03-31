package edu.ucam.restcrud.database.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = ['alumno_id', 'plan_id', 'curso']))
class AlumnoPlan {
    @Id
    @GeneratedValue
    Integer id

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    Alumno alumno

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    Plan plan

    // Solamente el año de inicio, no (año inicio - año fin)
    // Alternativamente, se podría hacer un @Embeddable
    Short curso

    Alumno getAlumno() {
        return alumno
    }

    void setAlumno(Alumno alumno) {
        this.alumno = alumno
    }

    Plan getPlan() {
        return plan
    }

    void setPlan(Plan plan) {
        this.plan = plan
    }

    Short getCurso() {
        return curso
    }

    void setCurso(Short curso) {
        this.curso = curso
    }
}
