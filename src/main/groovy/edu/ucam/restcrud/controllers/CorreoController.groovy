package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.beans.dtos.CorreoAltaDTO
import edu.ucam.restcrud.beans.dtos.CorreoDTO
import edu.ucam.restcrud.services.CorreoService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping(path = "/api/correos")
class CorreoController {
    @Autowired
    CorreoService correoService


    // [C]reate
    @PostMapping('/alumno/{id}')
    @ResponseBody ResponseEntity<?> crearCorreo(@PathVariable('id') Integer alumnoId, @Valid @RequestBody CorreoAltaDTO body) {
        Optional<CorreoDTO> res = correoService.addToAlumno(alumnoId, body)
        // Devolvemos OK o NOT FOUND dependiendo de si el ID del alumno es valido o no
        if (res.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(res.get())
        }
    }

    // [R]ead
    @GetMapping
    @ResponseBody Iterable<CorreoDTO> getCorreos() {
        return correoService.getAll()
    }
    @GetMapping("/alumno/{id}")
    @ResponseBody ResponseEntity<?> getCorreosFromAlumno(@PathVariable('id') Integer alumnoId) {
        Optional<List<CorreoDTO>> listaCorreos = correoService.getByUserId(alumnoId)
        if (listaCorreos.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(listaCorreos.get())
        }
    }

    // [U]pdate
    @PutMapping("/id/{id}")
    @ResponseBody ResponseEntity<?> updateCorreo(@PathVariable('id') Integer correoId, @RequestBody CorreoAltaDTO body) {
        Optional<CorreoDTO> newCorreo = correoService.updateCorreoById(correoId, body)
        if (newCorreo.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(newCorreo.get())
        }
    }
    @PutMapping("/correo/{correo}")
    @ResponseBody ResponseEntity<?> updateCorreoByCorreo(@PathVariable('correo') Integer correoId, @RequestBody CorreoAltaDTO body) {

    }
}
