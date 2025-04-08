package edu.ucam.restcrud.database.universidad.repositories

import edu.ucam.restcrud.database.universidad.entities.AlumnoPlan
import org.springframework.data.repository.CrudRepository

interface AlumnoPlanRepository extends CrudRepository<AlumnoPlan, Integer> {

}