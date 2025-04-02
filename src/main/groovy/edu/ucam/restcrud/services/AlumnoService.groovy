package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.AlumnoConPlanesDTO
import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import edu.ucam.restcrud.beans.dtos.AlumnoPlanAltaDTO

import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.database.entities.AlumnoPlan
import edu.ucam.restcrud.database.entities.Correo
import edu.ucam.restcrud.database.entities.Plan
import edu.ucam.restcrud.database.repositories.AlumnoPlanRepository
import edu.ucam.restcrud.database.repositories.AlumnoRepository
import edu.ucam.restcrud.database.repositories.PlanRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AlumnoService {
    @Autowired
    AlumnoRepository alumnoRepository
    @Autowired
    PlanRepository planRepository
    @Autowired
    AlumnoPlanRepository alumnoPlanRepository

    /**
     * Añade o edita un alumno en la base de datos
     *
     * @param alumno DTO con datos del alumno con o sin ID
     * @return Optional con los datos del alumno creado o modificado
     */
    Optional<AlumnoDTO> save(@Valid AlumnoDTO alumnoDto) {
        Alumno alumno
        // Crear
        if (alumnoDto.id == null) {
            alumno = new Alumno()
        }
        // Editar
        else if (alumnoRepository.existsById(alumnoDto.id)) {
            alumno = alumnoRepository.findById(alumnoDto.id).get()
        }
        // Error (editar alumno no encontrado)
        else {
            return Optional.empty()
        }

        alumno.setTipoDocumento(alumnoDto.getTipoDocumento())
        alumno.setNumeroDocumento(alumnoDto.getNumeroDocumento())
        alumno.setNombreCompleto(alumnoDto.getNombreCompleto())
        alumno.setFechaNacimiento(alumnoDto.getFechaNacimiento())
        alumnoRepository.save(alumno)
        return Optional.of(toDto(alumno))
    }

    /**
     * Devuelve todos los datos de alumnos
     *
     * @param completo booleano indicando si se deberían devolver TODOS los datos del alumno
     * @return Lista de datos de alumnos
     */
    List<AlumnoDTO> getAll(boolean completo = false) {
        Iterable<Alumno> alumnos = alumnoRepository.findAll()
        return toDto(alumnos, completo)
    }

    /**
     * Devuelve una lista de todos los alumnos
     * como entidades sin usar DTOs.
     *
     * Los correos y planes no se serializan
     *
     * @return Iterable de Alumnos
     */
    Iterable<Alumno> getAllRaw() {
        return alumnoRepository.findAll()
    }

    /**
     * Devuelve el alumno con la ID especificada
     *
     * @param id ID numérica del alumno
     * @param completo Booleano indicando si se deben mostrar todos los datos
     * @return un valor opcional con el alumno encontrado o nada
     */
    Optional<AlumnoDTO> get(Integer id, boolean completo = false) {
        Optional<Alumno> alumno = alumnoRepository.findById(id)
        // Recibe un Optional, devuelve un Optional
        return toDto(alumno, completo)
    }

    /**
     * Busca en la base de datos los alumnos cuyo nombre contenga el nombre indicado
     *
     * @param substring La sección del nombre a buscar
     * @param completo Booleano indicando si se deben mostrar todos los datos
     * @return List<AlumnoDTO> con los alumnos cuyo nombre contenga `substring`
     */
    List<AlumnoDTO> search(String substring, boolean completo = false) {
        Iterable<Alumno> alumnos = alumnoRepository.findAllByNombreCompletoIgnoreCaseContaining(substring)
        return toDto(alumnos, completo)
    }

    /**
     * Elimina un alumno de la base de datos a partir de su ID
     *
     * @param id Identificador único del alumno
     * @return true si se ha eliminado el alumno, false si no se ha eliminado
     */
    boolean delete(Integer id) {
        Optional<Alumno> optAlumno = alumnoRepository.findById(id)
        if (optAlumno.isEmpty()) {
            return false
        }

        // Borrar todos los registros de cursos para este alumno
        Alumno alumno = optAlumno.get()
        for (AlumnoPlan assoc : alumno.planAssoc) {
            alumnoPlanRepository.delete(assoc)
        }
        // Borrar alumno
        alumnoRepository.deleteById(id)
        return true
    }

    /**
     * Añade un plan con un curso a un alumno.
     *
     * @param id
     * @return
     */
    Optional<AlumnoConPlanesDTO> addPlan(AlumnoPlanAltaDTO alumnoPlan) {
        // Necesitamos que la ID de la relación sea NULL pero que la del alumno NO sea NULL
        if (alumnoPlan.id != null || alumnoPlan.alumno_id == null) {
            return Optional.empty()
        }

        Optional<Alumno> optAlumno = alumnoRepository.findById(alumnoPlan.alumno_id)
        Optional<Plan> optPlan = planRepository.findById(alumnoPlan.plan_id)
        if (optPlan.isEmpty() || optAlumno.isEmpty()) {
            return Optional.empty()
        }

        Alumno alumno = optAlumno.get()

        AlumnoPlan assoc = new AlumnoPlan()
        assoc.alumno = alumno
        assoc.plan = optPlan.get()
        assoc.curso = alumnoPlan.curso

        alumnoPlanRepository.save(assoc)
        return Optional.of(new AlumnoConPlanesDTO(alumno))
    }

    Optional<AlumnoConPlanesDTO> getPlanes(Integer alumnoId) {
        Optional<Alumno> alumno = alumnoRepository.findById(alumnoId)
        if (alumno.isEmpty()) {
            return Optional.empty()
        }
        return Optional.of(new AlumnoConPlanesDTO(alumno.get()))
    }

    Optional<AlumnoConPlanesDTO> updatePlan(AlumnoPlanAltaDTO alumnoPlanDto) {
        // Queremos modificar por ID de la relación, necesitamos que la ID del alumno SEA NULL
        if (alumnoPlanDto.id == null || alumnoPlanDto.alumno_id != null) {
            return Optional.empty()
        }

        Optional<AlumnoPlan> optAlumnoPlan = alumnoPlanRepository.findById(alumnoPlanDto.id)
        Optional<Plan> optPlan = planRepository.findById(alumnoPlanDto.plan_id)
        if (optAlumnoPlan.isEmpty() || optPlan.isEmpty()) {
            return Optional.empty()
        }

        AlumnoPlan alumnoPlan = optAlumnoPlan.get()

        alumnoPlan.curso = alumnoPlanDto.curso
        alumnoPlan.plan = optPlan.get()
        alumnoPlanRepository.save(alumnoPlan)

        return Optional.of(new AlumnoConPlanesDTO(alumnoPlan.alumno))
    }

    /**
     * @param alumnosPlanId
     * @return
     */
    boolean removePlan(Integer alumnosPlanId) {
        Optional<AlumnoPlan> alumnoPlan = alumnoPlanRepository.findById(alumnosPlanId)
        if (alumnoPlan.isEmpty()) {
            return false
        }
        alumnoPlanRepository.delete(alumnoPlan.get())
        return true
    }

    /**
     * TODO: Revisar interacción entre tablas/servicios/repositorios
     *
     * @param id ID del alumno
     * @return true si existe, false si no existe
     */
    boolean exists(Integer id) {
        return alumnoRepository.existsById(id)
    }

    /**
     * LLAMADO DESDE EL SERVICIO DE CORREO
     * ===================================
     * Añade un nuevo correo al alumno. Esto no guarda el correo
     * en la base de datos
     *
     * @param id ID del alumno
     * @param correo Entidad Correo a añadir
     */
    void addCorreoToAlumno(Integer id, Correo correo) {
        Optional<Alumno> alumno = alumnoRepository.findById(id)
        if (alumno.isEmpty()) {
            return
        }

        alumno.get().correos.add(correo)
    }

    /**
     * DEBUG:
     * Borra todos los contenidos de la tabla
     */
    void nuke() {
        alumnoRepository.deleteAll()
    }

    // TO DTO
    private static List<AlumnoDTO> toDto(List<Alumno> a, boolean completo = false) {
        a.stream().map(alumno -> {
            return new AlumnoDTO(alumno, completo)
        }).collect()
    }
    private static List<AlumnoDTO> toDto(Iterable<Alumno> a, boolean completo = false) {
        return toDto(a as List<Alumno>, completo)
    }
    private static AlumnoDTO toDto(Alumno a, boolean completo = false) {
        return new AlumnoDTO(a, completo)
    }
    private static Optional<AlumnoDTO> toDto(Optional<Alumno> a, boolean completo = false) {
        if (a.isEmpty()) {
            return Optional.empty()
        }
        Optional.of(new AlumnoDTO(a.get(), completo))
    }
}
