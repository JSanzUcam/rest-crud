package edu.ucam.restcrud.database.universidad.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import edu.ucam.restcrud.beans.enums.TipoEstudioEnum
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "plan")
class Plan {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id

    @NotNull
    @Column(
        unique = true,
        name = "nombre"
    )
    String nombre

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    TipoEstudioEnum tipo

    // Año de eliminación
    @Column(name = "borrar_en")
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
