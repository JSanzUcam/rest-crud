package edu.ucam.restcrud.database.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import edu.ucam.restcrud.beans.enums.TipoPersonaEnum
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
    @JsonIgnore
    List<Alumno> alumnos

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    List<Alumno> getAlumnos() {
        return alumnos
    }

    void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos
    }

    TipoPersonaEnum getTipo() {
        return tipo
    }

    void setTipo(TipoPersonaEnum tipo) {
        this.tipo = tipo
    }

    String getNombre() {
        return nombre
    }

    void setNombre(String nombre) {
        this.nombre = nombre
    }
}
