package edu.ucam.restcrud.database.entities

import edu.ucam.restcrud.beans.enums.TipoPersonaEnum
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.validation.constraints.NotNull

@Entity
class Plan {
    @Id
    @GeneratedValue
    Integer id

    @NotNull
    @Column(unique = true)
    String nombre

    @Enumerated(EnumType.STRING)
    TipoPersonaEnum tipo

    @ManyToMany(
        fetch = FetchType.LAZY,
        mappedBy = "planes"
    )
    List<Alumno> alumnos
}
