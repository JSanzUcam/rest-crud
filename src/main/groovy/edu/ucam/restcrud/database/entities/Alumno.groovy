package edu.ucam.restcrud.database.entities

import com.fasterxml.jackson.annotation.JsonFormat
import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany

@Entity
class Alumno {
    @Id
    @GeneratedValue
    Integer id

    // DNI es String por la letra
    @NotNull
    @Column(unique = true)
    String dni

    // Nombre con apellidos
    @NotNull
    String nombreCompleto

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date fechaNacimiento

    // One to many de correos
    @OneToMany(
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "alumno_id")
    List<Correo> correos

    // Many to Many de Planes
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "alumno_planes",
        joinColumns = @JoinColumn(name = "alumno_id"),
        inverseJoinColumns = @JoinColumn(name = "plan_id")
    )
    List<Plan> planes
}
