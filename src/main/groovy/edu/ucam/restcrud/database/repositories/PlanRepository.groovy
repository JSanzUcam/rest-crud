package edu.ucam.restcrud.database.repositories

import edu.ucam.restcrud.database.entities.Plan
import org.springframework.data.repository.CrudRepository

interface PlanRepository extends CrudRepository<Plan, Integer> {
    List<Plan> findByBorrarEnIsNullOrBorrarEnGreaterThan(Short year)
    Optional<Plan> findByIdAndBorrarEnIsNullOrBorrarEnGreaterThan(Integer id, Short year)
    Optional<Plan> findByNombre(String nombre)
}
