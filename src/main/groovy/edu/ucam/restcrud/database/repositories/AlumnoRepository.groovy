package edu.ucam.restcrud.database.repositories

import edu.ucam.restcrud.database.entities.Alumno
import org.springframework.data.repository.CrudRepository

interface AlumnoRepository extends CrudRepository<Alumno, Integer> {
    Optional<Alumno> findByNumeroDocumento(String numeroDocumento);
    List<Alumno> findAllByNombreCompletoIgnoreCaseContaining(String nombreCompleto);
}
