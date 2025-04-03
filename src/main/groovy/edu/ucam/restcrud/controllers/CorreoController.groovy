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
    CorreoDTO update(
        @RequestParam('id') Integer correoId,
        @Valid @RequestBody CorreoAltaDTO body
    ) {
        return correoService.updateById(correoId, body)
    }
    // [D]elete
    @DeleteMapping
    ResponseEntity<?> delete(@RequestParam('id') Integer correoId) {
        correoService.delete(correoId)
        return ResponseEntity.ok("Eliminado correctamente")
    }
}
