package edu.ucam.restcrud.database.universidad.entities

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = ['alumno_id', 'plan_id', 'curso']),
    name = "alumno_plan"
)
class AlumnoPlan {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id

    @ManyToOne
    @JoinColumn(
        nullable = false,
        referencedColumnName = "id",
        name = "alumno_id"
    )
    Alumno alumno

    @ManyToOne
    @JoinColumn(
        nullable = false,
        referencedColumnName = "id",
        name = "plan_id"
    )
    Plan plan

    // Solamente el año de inicio, no (año inicio - año fin)
    @Column(name = "curso")
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
