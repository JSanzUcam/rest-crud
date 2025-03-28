package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.beans.dtos.AlumnoDTO

import edu.ucam.restcrud.beans.dtos.AlumnoFullDTO
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
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    @Autowired
    CorreoService correoService

    Logger logger = LoggerFactory.getLogger(AlumnoController.class)

    // [C]reate
    @PostMapping
    @ResponseBody ResponseEntity<AlumnoDTO> create(@Valid @RequestBody AlumnoDTO entradaDto) {
        // Nos aseguramos de que no estamos modificando ningun alumno existente
        entradaDto.id = null
        AlumnoDTO respuestaDto = alumnoService.save(entradaDto).get()
        ResponseEntity.ok(respuestaDto)
    }

    // [R]ead
    @GetMapping
    @ResponseBody ResponseEntity<?> get(
        @RequestParam(name = "id", required = false) Integer id,
        @RequestParam(name = "numeroDocumento", required = false) String numeroDocumento,
        @RequestParam(name = 'completo', required = false) boolean completo
    ) {
        // Si no estamos buscando por ID o Numero de Documento mostramos todo.
        if (!(id || numeroDocumento)) {
            return ResponseEntity.ok(alumnoService.getAll(completo))
        }

        Optional<AlumnoDTO> optAlumno
        if (id) {
            optAlumno = alumnoService.get(id, completo)
        } else {
            optAlumno = alumnoService.get(numeroDocumento, completo)
        }

        if (optAlumno.empty) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(optAlumno.get())
        }
    }
    @GetMapping("/buscar")
    @ResponseBody List<AlumnoDTO> search(@RequestParam("nombre") String nombre) {
        return alumnoService.findWithNameContaining(nombre)
    }
    /**
     * Demuestra el uso de @JsonIgnore desde la entidad.
     * Este metodo devuelve los alumnos sin sus correos o planes
     *
     * @return todos los alumnos como entidades, no como DTO.
     */
    @GetMapping("/test-ignore")
    @ResponseBody Iterable<Alumno> getAllRaw() {
        return alumnoService.getAllRaw()
    }

    // [U]pdate
    @PutMapping
    @ResponseBody ResponseEntity<?> update(
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
    @PutMapping("/plan")
    @ResponseBody ResponseEntity<?> addPlan(
        @RequestParam(name = "id") Integer id,
        @RequestBody Map<String, Object> plan
    ) {
        // El JSON es valido?
        boolean containsNombre = plan.containsKey("nombre")
        boolean containsId = plan.containsKey("id")
        if (!(containsId || containsNombre))
            return ResponseEntity.badRequest().build()

        Optional<AlumnoFullDTO> alumno
        if (containsNombre) {
            alumno = alumnoService.addPlan(id, plan.get("nombre") as String)
        } else {
            alumno = alumnoService.addPlan(id, plan.get("id") as Integer)
        }

        if (alumno.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(alumno.get())
        }
    }

    // [D]elete
    @DeleteMapping
    @ResponseBody ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        if (!alumnoService.delete(id)) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok("Eliminado correctamente")
        }
    }

    // CORREO
    @PostMapping("/correo")
    @ResponseBody ResponseEntity<?> createCorreo(
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
    @GetMapping("/correo")
    @ResponseBody ResponseEntity<?> getCorreoFromAlumno(@RequestParam('id') Integer alumnoId) {
        Optional<List<CorreoDTO>> listaCorreos = correoService.getByUserId(alumnoId)
        if (listaCorreos.isEmpty()) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok(listaCorreos.get())
        }
    }

    // DEBUG: Nuke. Borra todos los contenidos de la tabla
    @DeleteMapping(path = "/nuke")
    @ResponseBody void deleteAll() {
        logger.warn("Datos de alumnos eliminados por completo")
        alumnoService.nuke()
    }
}
