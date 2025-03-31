package edu.ucam.restcrud.database.entities

import edu.ucam.restcrud.database.embeddable.AlumnoPlanId
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
@IdClass(AlumnoPlanId.class)
class AlumnoPlan {
    @Id
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    Alumno alumno

    @Id
    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    Plan plan

    // Solamente el año de inicio, no (año inicio - año fin)
    // Alternativamente, se podría hacer un @Embeddable
    Short curso
}
