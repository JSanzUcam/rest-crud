package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.beans.dtos.*
import edu.ucam.restcrud.controllers.exceptions.BadCreateException
import edu.ucam.restcrud.controllers.exceptions.BadUpdateException
import edu.ucam.restcrud.controllers.exceptions.enums.BadCreateEnum
import edu.ucam.restcrud.controllers.exceptions.enums.BadUpdateEnum
import edu.ucam.restcrud.controllers.exceptions.enums.EntityType
import edu.ucam.restcrud.database.universidad.entities.Alumno
import edu.ucam.restcrud.services.AlumnoService
import edu.ucam.restcrud.services.CorreoService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    AlumnoDTO create(@Valid @RequestBody AlumnoDTO entradaDto) {
        // Nos aseguramos de que no estamos intentando modificar ningún alumno
        if (entradaDto.id != null) {
            throw new BadCreateException(EntityType.ALUMNO, BadCreateEnum.ENTITY_ID_SPECIFIED)
        }
        return alumnoService.save(entradaDto)
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
        AlumnoDTO alumno = alumnoService.get(id, completo)
        return ResponseEntity.ok(alumno)
    }
    // [U]pdate
    @PutMapping
    AlumnoDTO update(
        @Valid @RequestBody AlumnoDTO dto
    ) {
        if (dto.id == null) {
            throw new BadUpdateException(EntityType.ALUMNO, BadUpdateEnum.ENTITY_ID_NOT_SPECIFIED)
        }
        return alumnoService.save(dto)
    }
    // [D]elete
    @DeleteMapping
    ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        alumnoService.delete(id)
        return ResponseEntity.ok("Eliminado correctamente")
    }

    // BUSCAR
    @GetMapping("/buscar")
    List<AlumnoDTO> search(@RequestParam("nombre") String nombre) {
        return alumnoService.search(nombre)
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
    CorreoDTO createCorreo(
        @RequestParam('id') Integer alumnoId,
        @Valid @RequestBody CorreoAltaDTO body
    ) {
        return correoService.addToAlumno(alumnoId, body)
    }
    @GetMapping("/correos")
    ResponseEntity<?> getCorreos(@RequestParam('id') Integer alumnoId) {
        List<CorreoDTO> listaCorreos = correoService.getByUserId(alumnoId)
        ResponseEntity.ok(listaCorreos)
    }

    // CURSOS
    @PostMapping("/cursos")
    AlumnoDTO addPlan(@Valid @RequestBody AlumnoPlanAltaDTO alumnoPlan) {
        return alumnoService.addPlan(alumnoPlan)
    }
    @GetMapping("/cursos")
    AlumnoConPlanesDTO getPlanes(@RequestParam("id") Integer alumnoId) {
        return alumnoService.getPlanes(alumnoId)
    }
    @PutMapping("/cursos")
    AlumnoDTO updatePlan(@Valid @RequestBody AlumnoPlanAltaDTO alumnoPlan) {
        return alumnoService.updatePlan(alumnoPlan)
    }
    @DeleteMapping("/cursos")
    ResponseEntity<?> removePlan(@RequestParam("id") Integer alumnoPlanId) {
        alumnoService.removePlan(alumnoPlanId)
        return ResponseEntity.ok("Eliminado correctamente")
    }

    // JDBC Template
    @GetMapping("/unitemplate")
    List<Map<String, Object>> getUniTemplateQuery() {
        return alumnoService.getUniTemplateQuery()
    }
    @GetMapping("/otrotemplate")
    List<Map<String, Object>> getOtroTemplateQuery() {
        return alumnoService.getOtroTemplateQuery()
    }

    // DEBUG: Nuke. Borra todos los contenidos de la tabla
    @DeleteMapping(path = "/nuke")
    void deleteAll() {
        logger.warn("Datos de alumnos eliminados por completo")
        alumnoService.nuke()
    }
}
