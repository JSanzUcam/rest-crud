package edu.ucam.restcrud.controllers


import edu.ucam.restcrud.beans.dtos.AlumnoDTO


import edu.ucam.restcrud.beans.dtos.AlumnoPlanAltaDTO
import edu.ucam.restcrud.beans.dtos.CorreoAltaDTO
import edu.ucam.restcrud.beans.dtos.CorreoDTO

import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.services.AlumnoService
import edu.ucam.restcrud.services.CorreoService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = "/api/alumnos")
class AlumnoController {
    @Autowired
    AlumnoService alumnoService
    @Autowired
    CorreoService correoService

    Logger logger = LoggerFactory.getLogger(AlumnoController.class)

    // [C]reate
    @PostMapping
    ResponseEntity<AlumnoDTO> create(@Valid @RequestBody AlumnoDTO entradaDto) {
        // Nos aseguramos de que no estamos intentando modificar ningún alumno
        if (entradaDto.id != null) {
            return ResponseEntity.badRequest().build()
        }
        AlumnoDTO respuestaDto = alumnoService.save(entradaDto).get()
        ResponseEntity.ok(respuestaDto)
    }
    // [R]ead
    @GetMapping
    ResponseEntity<?> get(
        @RequestParam(name = "id", required = false) Integer id,
        @RequestParam(name = 'completo', required = false) boolean completo
    ) {
        // Si no estamos buscando nada mostramos todo.
        if (!id) {
            return ResponseEntity.ok(alumnoService.getAll(completo))
        }

        Optional<AlumnoDTO> optAlumno = alumnoService.get(id, completo)
        if (optAlumno.empty) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(optAlumno.get())
        }
    }
    // [U]pdate
    @PutMapping
    ResponseEntity<?> update(
            @Valid @RequestBody AlumnoDTO dto
    ) {
        if (dto.id == null) {
            return ResponseEntity.badRequest().build()
        }

        Optional<AlumnoDTO> alumnoOpt = alumnoService.save(dto)
        if (alumnoOpt.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(alumnoOpt.get())
        }
    }
    // [D]elete
    @DeleteMapping
    ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        if (!alumnoService.delete(id)) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok("Eliminado correctamente")
        }
    }

    // BUSCAR
    @GetMapping("/buscar")
    List<AlumnoDTO> search(@RequestParam("nombre") String nombre) {
        return alumnoService.findWithNameContaining(nombre)
    }

    /**
     * Demuestra el uso de @JsonIgnore desde la entidad.
     * Este método devuelve los alumnos sin sus correos o planes
     *
     * @return todos los alumnos como entidades, no como DTO.
     */
    @GetMapping("/test-ignore")
    Iterable<Alumno> getAllRaw() {
        return alumnoService.getAllRaw()
    }

    // CORREO
    @PostMapping("/correos")
    ResponseEntity<?> createCorreo(
            @RequestParam('id') Integer alumnoId,
            @Valid @RequestBody CorreoAltaDTO body
    ) {
        Optional<CorreoDTO> res = correoService.addToAlumno(alumnoId, body)
        if (res.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(res.get())
        }
    }
    @GetMapping("/correos")
    ResponseEntity<?> getCorreoFromAlumno(@RequestParam('id') Integer alumnoId) {
        Optional<List<CorreoDTO>> listaCorreos = correoService.getByUserId(alumnoId)
        if (listaCorreos.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(listaCorreos.get())
        }
    }

    // CURSOS
    @PutMapping("/cursos")
    ResponseEntity<?> addPlan(@Valid @RequestBody AlumnoPlanAltaDTO alumnosPlan) {
        Optional<AlumnoDTO> alumno = alumnoService.addPlan(alumnosPlan)

        if (alumno.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(alumno.get())
        }
    }
    @DeleteMapping("/cursos")
    ResponseEntity<?> removePlan(@RequestParam("id") Integer alumnoPlanId) {
        if (!alumnoService.removePlan(alumnoPlanId)) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok("Eliminado correctamente")
        }
    }

    // DEBUG: Nuke. Borra todos los contenidos de la tabla
    @DeleteMapping(path = "/nuke")
    void deleteAll() {
        logger.warn("Datos de alumnos eliminados por completo")
        alumnoService.nuke()
    }
}
