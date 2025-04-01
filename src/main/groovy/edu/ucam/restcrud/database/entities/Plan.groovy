package edu.ucam.restcrud.database.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import edu.ucam.restcrud.beans.enums.TipoEstudioEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
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
    TipoEstudioEnum tipo

    // Año de eliminación
    Short borrarEn

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "plan"
    )
    @JsonIgnore
    List<AlumnoPlan> alumnoAssoc

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getNombre() {
        return nombre
    }

    void setNombre(String nombre) {
        this.nombre = nombre
    }

    TipoEstudioEnum getTipo() {
        return tipo
    }

    void setTipo(TipoEstudioEnum tipo) {
        this.tipo = tipo
    }

    Short getBorrarEn() {
        return borrarEn
    }

    void setBorrarEn(Short borrarEn) {
        this.borrarEn = borrarEn
    }

    List<AlumnoPlan> getAlumnoAssoc() {
        return alumnoAssoc
    }

    void setAlumnoAssoc(List<AlumnoPlan> alumnoAssoc) {
        this.alumnoAssoc = alumnoAssoc
    }

    List<Alumno> getAlumnos() {
        alumnoAssoc
            .stream()
            .map(alumno -> {
                alumno.alumno
            })
            .collect()
    }
}
