package edu.ucam.restcrud.database.entities

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
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
    @JsonIgnore
    List<Correo> correos

    // Many to Many de Planes
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
        name = "alumno_planes",
        joinColumns = @JoinColumn(name = "alumno_id"),
        inverseJoinColumns = @JoinColumn(name = "plan_id")
    )
    List<Plan> planes

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getDni() {
        return dni
    }

    void setDni(String dni) {
        this.dni = dni
    }

    String getNombreCompleto() {
        return nombreCompleto
    }

    void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto
    }

    Date getFechaNacimiento() {
        return fechaNacimiento
    }

    void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento
    }

    List<Correo> getCorreos() {
        return correos
    }

    void setCorreos(List<Correo> correos) {
        this.correos = correos
    }

    List<Plan> getPlanes() {
        return planes
    }

    void setPlanes(List<Plan> planes) {
        this.planes = planes
    }
}
