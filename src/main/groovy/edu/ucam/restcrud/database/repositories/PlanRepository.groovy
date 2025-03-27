package edu.ucam.restcrud.database.repositories

import edu.ucam.restcrud.database.entities.Plan
import org.springframework.data.repository.CrudRepository

interface PlanRepository extends CrudRepository<Plan, Integer> {
    Optional<Plan> findByNombre(String nombre)
}
