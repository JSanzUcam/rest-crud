package edu.ucam.restcrud.database.otro.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "otro_entity")
class OtroEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id

    @Column(name = "mensaje")
    String mensaje
}
