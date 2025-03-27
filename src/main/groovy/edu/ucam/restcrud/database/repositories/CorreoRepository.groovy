package edu.ucam.restcrud.database.repositories

import edu.ucam.restcrud.database.entities.Correo
import org.springframework.data.repository.CrudRepository

interface CorreoRepository extends CrudRepository<Correo, Integer> {
    Optional<Correo> findByCorreo(String correo);
    void deleteByCorreo(String correo);
}