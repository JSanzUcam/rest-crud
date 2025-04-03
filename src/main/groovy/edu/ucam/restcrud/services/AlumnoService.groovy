package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.AlumnoConPlanesDTO
import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import edu.ucam.restcrud.beans.dtos.AlumnoPlanAltaDTO
import edu.ucam.restcrud.controllers.exceptions.BadCreateException
import edu.ucam.restcrud.controllers.exceptions.BadUpdateException
import edu.ucam.restcrud.controllers.exceptions.InvalidArgumentsException
import edu.ucam.restcrud.controllers.exceptions.NotFoundException
import edu.ucam.restcrud.controllers.exceptions.enums.BadCreateEnum
import edu.ucam.restcrud.controllers.exceptions.enums.BadUpdateEnum
import edu.ucam.restcrud.controllers.exceptions.enums.EntityType
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
    AlumnoDTO save(@Valid AlumnoDTO alumnoDto) {
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
            throw new NotFoundException(EntityType.ALUMNO, alumnoDto.id)
        }

        alumno.setTipoDocumento(alumnoDto.getTipoDocumento())
        alumno.setNumeroDocumento(alumnoDto.getNumeroDocumento())
        alumno.setNombreCompleto(alumnoDto.getNombreCompleto())
        alumno.setFechaNacimiento(alumnoDto.getFechaNacimiento())
        alumnoRepository.save(alumno)
        return toDto(alumno)
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
     * @return el alumno encontrado
     */
    AlumnoDTO get(Integer id, boolean completo = false) {
        Optional<Alumno> alumno = alumnoRepository.findById(id)
        if (alumno.isEmpty()) {
            throw new NotFoundException(EntityType.ALUMNO, id)
        }
        return toDto(alumno.get(), completo)
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
     */
    void delete(Integer id) {
        Optional<Alumno> optAlumno = alumnoRepository.findById(id)
        if (optAlumno.isEmpty()) {
            throw new NotFoundException(EntityType.ALUMNO, id)
        }

        // Borrar todos los registros de cursos para este alumno
        Alumno alumno = optAlumno.get()
        for (AlumnoPlan assoc : alumno.planAssoc) {
            alumnoPlanRepository.delete(assoc)
        }
        // Borrar alumno
        alumnoRepository.deleteById(id)
    }

    /**
     * Añade un plan con un curso a un alumno.
     *
     * @param id
     * @return
     */
    AlumnoConPlanesDTO addPlan(AlumnoPlanAltaDTO alumnoPlan) {
        // Necesitamos que la ID de la relación sea NULL pero que la del alumno NO sea NULL
        if (alumnoPlan.id != null || alumnoPlan.alumno_id == null) {
            throw new BadCreateException(EntityType.ALUMNO_PLAN, BadCreateEnum.RELATIONSHIP_ID_SPECIFIED)
        }

        Optional<Alumno> optAlumno = alumnoRepository.findById(alumnoPlan.alumno_id)
        Optional<Plan> optPlan = planRepository.findById(alumnoPlan.plan_id)
        if (optPlan.isEmpty()) {
            throw new NotFoundException(EntityType.ALUMNO, alumnoPlan.alumno_id)
        } else if (optAlumno.isEmpty()) {
            throw new NotFoundException(EntityType.PLAN, alumnoPlan.plan_id)
        }

        // No podemos apuntarnos a un curso mayor que la fecha de eliminación
        Short anyoMaximo = optPlan.get().borrarEn
        if (anyoMaximo != null) {
            if (alumnoPlan.curso >= anyoMaximo) {
                throw new InvalidArgumentsException(EntityType.ALUMNO_PLAN, "año menor que " + anyoMaximo, alumnoPlan.curso)
            }
        }

        Alumno alumno = optAlumno.get()

        AlumnoPlan assoc = new AlumnoPlan()
        assoc.alumno = alumno
        assoc.plan = optPlan.get()
        assoc.curso = alumnoPlan.curso

        alumnoPlanRepository.save(assoc)
        return new AlumnoConPlanesDTO(alumno)
    }

    AlumnoConPlanesDTO getPlanes(Integer alumnoId) {
        Optional<Alumno> alumno = alumnoRepository.findById(alumnoId)
        if (alumno.isEmpty()) {
            throw new NotFoundException(EntityType.ALUMNO, alumnoId)
        }
        return new AlumnoConPlanesDTO(alumno.get())
    }

    AlumnoConPlanesDTO updatePlan(AlumnoPlanAltaDTO alumnoPlanDto) {
        // Queremos modificar por ID de la relación, necesitamos que la ID del alumno SEA NULL
        if (alumnoPlanDto.id == null || alumnoPlanDto.alumno_id != null) {
            throw new BadUpdateException(EntityType.ALUMNO_PLAN, BadUpdateEnum.RELATIONSHIP_ID_NOT_SPECIFIED)
        }

        Optional<AlumnoPlan> optAlumnoPlan = alumnoPlanRepository.findById(alumnoPlanDto.id)
        Optional<Plan> optPlan = planRepository.findById(alumnoPlanDto.plan_id)

        if (optAlumnoPlan.isEmpty()) {
            throw new NotFoundException(EntityType.ALUMNO_PLAN, alumnoPlanDto.id)
        } else if (optPlan.isEmpty()) {
            throw new NotFoundException(EntityType.PLAN, alumnoPlanDto.plan_id)
        }

        AlumnoPlan alumnoPlan = optAlumnoPlan.get()

        alumnoPlan.curso = alumnoPlanDto.curso
        alumnoPlan.plan = optPlan.get()
        alumnoPlanRepository.save(alumnoPlan)

        return new AlumnoConPlanesDTO(alumnoPlan.alumno)
    }

    /**
     * @param alumnosPlanId
     */
    void removePlan(Integer alumnosPlanId) {
        Optional<AlumnoPlan> alumnoPlan = alumnoPlanRepository.findById(alumnosPlanId)
        if (alumnoPlan.isEmpty()) {
            throw new NotFoundException(EntityType.ALUMNO_PLAN, alumnosPlanId)
        }
        alumnoPlanRepository.delete(alumnoPlan.get())
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
}
