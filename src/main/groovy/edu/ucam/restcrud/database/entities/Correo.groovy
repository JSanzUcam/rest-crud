package edu.ucam.restcrud.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

import java.sql.Timestamp

@Entity
@Table(name = "correo")
class Correo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id

    @Column(
        unique = true,
        name = "correo"
    )
    String correo

    @CreationTimestamp
    @Column(name = "fecha_alta")
    Timestamp fechaAlta
    @UpdateTimestamp
    @Column(name = "fecha_modificacion")
    Timestamp fechaModificacion

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    Timestamp getFechaModificacion() {
        return fechaModificacion
    }

    void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion
    }

    Timestamp getFechaAlta() {
        return fechaAlta
    }

    void setFechaAlta(Timestamp fechaAlta) {
        this.fechaAlta = fechaAlta
    }

    String getCorreo() {
        return correo
    }

    void setCorreo(String correo) {
        this.correo = correo
    }
}
