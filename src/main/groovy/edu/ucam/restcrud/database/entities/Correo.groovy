package edu.ucam.restcrud.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
class Correo {
    @Id
    @GeneratedValue
    Integer id

    @Column(unique = true)
    String correo

    @CreationTimestamp
    Date fechaAlta
    @UpdateTimestamp
    Date fechaModificacion
}
