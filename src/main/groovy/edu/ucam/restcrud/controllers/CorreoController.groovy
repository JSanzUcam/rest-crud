package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.beans.dtos.CorreoAltaDTO
import edu.ucam.restcrud.beans.dtos.CorreoDTO
import edu.ucam.restcrud.services.CorreoService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// TODO: RestControllerAdvice para control de errores global

@RestController
@RequestMapping(path = "/api/correos")
class CorreoController {
    @Autowired
    CorreoService correoService

    // [R]ead
    @GetMapping
    Iterable<CorreoDTO> getAll() {
        return correoService.getAll()
    }
    // [U]pdate
    @PutMapping()
    ResponseEntity<?> updateById(
        @RequestParam('id') Integer correoId,
        @Valid @RequestBody CorreoAltaDTO body
    ) {
        Optional<CorreoDTO> newCorreo = correoService.updateById(correoId, body)
        if (newCorreo.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(newCorreo.get())
        }
    }
    // [D]elete
    @DeleteMapping
    ResponseEntity<?> deleteById(@RequestParam('id') Integer correoId) {
        if (!correoService.deleteById(correoId)) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok("Eliminado correctamente")
        }
    }
}
