package edu.ucam.restcrud.database.entities

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import edu.ucam.restcrud.beans.enums.TipoDocumentoEnum
import groovyjarjarantlr4.v4.runtime.misc.NotNull
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = ['tipo_documento', 'numero_documento']),
    name = "alumno"
)
class Alumno {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    TipoDocumentoEnum tipoDocumento

    @NotNull
    @Column(name = "numero_documento")
    String numeroDocumento

    // Nombre con apellidos
    @NotNull
    @Column(name = "nombre_completo")
    String nombreCompleto

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento")
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
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "alumno"
    )
    @JsonIgnore
    List<AlumnoPlan> planAssoc

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    TipoDocumentoEnum getTipoDocumento() {
        return tipoDocumento
    }

    void setTipoDocumento(TipoDocumentoEnum tipoDocumento) {
        this.tipoDocumento = tipoDocumento
    }

    String getNumeroDocumento() {
        return numeroDocumento
    }

    void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento
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

    List<AlumnoPlan> getPlanAssoc() {
        return planAssoc
    }

    void setPlanAssoc(List<AlumnoPlan> planAssoc) {
        this.planAssoc = planAssoc
    }
}
