package edu.ucam.restcrud.database.repositories

import edu.ucam.restcrud.database.entities.Alumno
import org.springframework.data.repository.CrudRepository

interface AlumnoRepository extends CrudRepository<Alumno, Integer> {
    Optional<Alumno> findByDni(String dni);
    List<Alumno> findAllByNombreCompletoIgnoreCaseContaining(String nombreCompleto);
}
