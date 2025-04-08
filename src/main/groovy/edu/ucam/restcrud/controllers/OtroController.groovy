package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.database.otro.entities.OtroEntity
import edu.ucam.restcrud.services.OtroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/otro")
class OtroController {
    @Autowired
    OtroService otroService

    @PostMapping
    OtroEntity create(@RequestParam("mensaje") String mensaje) {
        return otroService.create(mensaje)
    }

    @GetMapping
    List<OtroEntity> read() {
        return otroService.getAll()
    }

    @PutMapping
    OtroEntity update(
        @RequestParam("id") Integer id,
        @RequestParam("mensaje") String mensaje
    ) {
        return otroService.update(id, mensaje)
    }

    @DeleteMapping
    void delete(@RequestParam("id") Integer id) {
        otroService.delete(id)
    }
}
