package edu.ucam.restcrud.database.universidad.repositories

import edu.ucam.restcrud.database.universidad.entities.Alumno
import org.springframework.data.repository.CrudRepository

interface AlumnoRepository extends CrudRepository<Alumno, Integer> {
    Optional<Alumno> findByNumeroDocumento(String numeroDocumento);
    List<Alumno> findAllByNombreCompletoIgnoreCaseContaining(String nombreCompleto);
}
