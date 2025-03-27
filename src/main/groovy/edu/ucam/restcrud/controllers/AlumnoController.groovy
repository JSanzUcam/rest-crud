package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.beans.dtos.AlumnoAltaDTO
import edu.ucam.restcrud.services.AlumnoService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping(path = "/api/alumnos")
class AlumnoController {
    @Autowired
    AlumnoService alumnoService

    Logger logger = LoggerFactory.getLogger(AlumnoController.class)

    // [C]reate
    @PostMapping
    @ResponseBody ResponseEntity<AlumnoDTO> addAlumno(@Valid @RequestBody AlumnoAltaDTO entradaDto) {
        AlumnoDTO respuestaDto = alumnoService.create(entradaDto)
        ResponseEntity.ok(respuestaDto)
    }

    // [R]ead
    @GetMapping
    @ResponseBody Iterable<AlumnoDTO> getAlumnos(@RequestParam(name = 'correos', required = false) boolean correos) {
        return alumnoService.getAll(correos)
    }
    @GetMapping(path = "/id/{id}")
    @ResponseBody ResponseEntity<?> getAlumnoById(@PathVariable("id") Integer id, @RequestParam(name = 'correos', required = false) boolean correos) {
        Optional<AlumnoDTO> optAlumno = alumnoService.get(id, correos)
        if (optAlumno.empty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen alumnos con esa ID")
        }
        return ResponseEntity.ok(optAlumno.get())
    }
    @GetMapping(path = "/dni/{dni}")
    @ResponseBody ResponseEntity<?> getAlumnosByDni(@PathVariable("dni") String dni) {
        Optional<Alumno> optAlumno = alumnoService.get(dni)
        if (optAlumno.empty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen alumnos con esa ID")
        }
        return ResponseEntity.ok(optAlumno.get())
    }
    @GetMapping(path = "/nombre/{nombre}")
    @ResponseBody Iterable<Alumno> getAlumnosByNombre(@PathVariable("nombre") String nombre) {
        return alumnoService.findWithNameContaining(nombre)
    }

    // [U]pdate
    @PutMapping(path = "/{id}")
    @ResponseBody ResponseEntity<?> updateAlumno(@Valid @RequestBody AlumnoAltaDTO dto, @PathVariable("id") Integer id) {
        if (alumnoService.update(id, dto))
            return ResponseEntity.ok("Alumno modificado")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen alumnos con esa ID")
    }

    // [D]elete
    @DeleteMapping(path = "/{id}")
    @ResponseBody ResponseEntity<?> deleteAlumno(@PathVariable("id") Integer id) {
        if (!alumnoService.delete(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen alumnos con esa ID")
        return ResponseEntity.ok("Alumno eliminado")
    }

    // DEBUG: Nuke. Borra todos los contenidos de la tabla
    @DeleteMapping(path = "/nuke")
    @ResponseBody void deleteAll() {
        logger.warn("Datos de alumnos eliminados por completo")
        alumnoService.nuke()
    }
}
