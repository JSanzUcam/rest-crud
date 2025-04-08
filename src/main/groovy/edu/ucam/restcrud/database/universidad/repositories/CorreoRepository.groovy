package edu.ucam.restcrud.database.universidad.repositories

import edu.ucam.restcrud.database.universidad.entities.Correo
import org.springframework.data.repository.CrudRepository

interface CorreoRepository extends CrudRepository<Correo, Integer> {

}