package edu.ucam.restcrud.services

import edu.ucam.restcrud.controllers.exceptions.NotFoundException
import edu.ucam.restcrud.controllers.exceptions.enums.EntityType
import edu.ucam.restcrud.database.otro.entities.OtroEntity
import edu.ucam.restcrud.database.otro.repositories.OtroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OtroService {
    @Autowired
    OtroRepository otroRepository

    OtroEntity create(String mensaje) {
        OtroEntity otro = new OtroEntity()
        otro.mensaje = mensaje
        otroRepository.save(otro)
        return otro
    }

    List<OtroEntity> getAll() {
        return otroRepository.findAll() as List
    }

    OtroEntity update(Integer id, String mensaje) {
        Optional<OtroEntity> otro = otroRepository.findById(id)
        if (otro.isEmpty()) {
            throw new NotFoundException(EntityType.OTRO, id)
        }
        otro.get().mensaje = mensaje
        otroRepository.save(otro.get())
        return otro.get()
    }

    void delete(Integer id) {
        if (otroRepository.existsById(id)) {
            throw new NotFoundException(EntityType.OTRO, id)
        }
        otroRepository.deleteById(id)
    }
}
