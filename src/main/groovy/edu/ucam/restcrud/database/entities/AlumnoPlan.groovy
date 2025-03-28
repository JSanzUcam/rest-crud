package edu.ucam.restcrud.database.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class AlumnoPlan {
    @Id
    @GeneratedValue
    Integer id

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    Alumno alumno

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    Plan plan

    // Solamente el año de inicio, no (año inicio - año fin)
    Short curso
}
