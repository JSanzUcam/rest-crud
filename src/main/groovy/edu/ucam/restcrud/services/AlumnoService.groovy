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
        if (alumnoDto.id == null) {
            // INFO: Groovy da un aviso aquí a pesar de que si que estemos usando
            //       esta asignación. Pasa lo mismo en la siguiente
            //noinspection GroovyUnusedAssignment
            alumno = new Alumno()
        } else if (alumnoRepository.existsById(alumnoDto.id)) {
            //noinspection GroovyUnusedAssignment
            alumno = alumnoRepository.findById(alumnoDto.id).get()
        }

        // Si no se ha encontrado el alumno devolvemos empty
        if (alumno == null) {
            return Optional.empty()
        }

        alumno.setTipoDocumento(alumnoDto.getTipoDocumento())
        alumno.setNumeroDocumento(alumnoDto.getNumeroDocumento())
        alumno.setNombreCompleto(alumnoDto.getNombreCompleto())
        alumno.setFechaNacimiento(alumnoDto.getFechaNacimiento())
        alumnoRepository.save(alumno)
        return Optional.of(new AlumnoDTO(alumno))
    }

    /**
     * Devuelve todos los datos de alumnos
     *
     * @param completo booleano indicando si se deberían devolver TODOS los datos del alumno
     * @return Lista de datos de alumnos
     */
    List<AlumnoDTO> getAll(boolean completo = false) {
        List<Alumno> alumnos = alumnoRepository.findAll() as List<Alumno>
        return alumnos.stream().map(a -> {
            return new AlumnoDTO(a, completo)
        }).collect()
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
        return optionalDtoFromOptionalAlumno(alumno, completo)
    }

    /**
     * Busca en la base de datos los alumnos cuyo nombre contenga el nombre indicado
     *
     * @param substring La sección del nombre a buscar
     * @param completo Booleano indicando si se deben mostrar todos los datos
     * @return List<AlumnoDTO> con los alumnos cuyo nombre contenga `substring`
     */
    List<AlumnoDTO> findWithNameContaining(String substring, boolean completo = false) {
        List<Alumno> alumnos = alumnoRepository.findAllByNombreCompletoIgnoreCaseContaining(substring) as List<Alumno>
        return alumnos
            .stream()
            .map(alumno -> {
                return new AlumnoDTO(alumno, completo)
            })
            .collect()
    }

    /**
     * Actualiza el alumno para asignarle un plan y un curso.
     *
     * @param id
     * @return
     */
    Optional<AlumnoConPlanesDTO> addPlan(AlumnoPlanAltaDTO alumnoPlan) {
        Optional<Alumno> optAlumno = alumnoRepository.findById(alumnoPlan.alumno_id)
        if (optAlumno.isEmpty()) {
            return Optional.empty()
        }
        Optional<Plan> optPlan = planRepository.findById(alumnoPlan.plan_id)
        return addOptionalPlan(optPlan, optAlumno.get(), alumnoPlan.curso)
    }

    /**
     * Elimina un alumno de la base de datos a partir de su ID
     *
     * TODO: Pasar de usar BOOL a usar HTTP STATUS CODES
     *
     * @param id Identificador único del alumno
     * @return true si se ha eliminado el alumno, false si no se ha eliminado
     */
    boolean delete(Integer id) {
        if (alumnoRepository.findById(id).empty) {
            return false
        }

        // Borrar todos los registros de cursos para este alumno
        Alumno alumno = alumnoRepository.findById(id).get()
        for (AlumnoPlan assoc : alumno.planAssoc) {
            alumnoPlanRepository.delete(assoc)
        }
        // Borrar alumno
        alumnoRepository.deleteById(id)
        return true
    }

    /**
     * TODO: Pasar de usar BOOL a usar HTTP STATUS CODES
     *
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
     * @param id ID del alumno
     * @return true si existe, false si no existe
     */
    boolean exists(Integer id) {
        return alumnoRepository.existsById(id)
    }

    /**
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


    // TODO: Cambiar este nombre, wtf?
    private static Optional<AlumnoDTO> optionalDtoFromOptionalAlumno(Optional<Alumno> alumno, boolean completo = false) {
        if (alumno.empty) {
            return Optional.empty()
        }

        AlumnoDTO alumnoDTO = new AlumnoDTO(alumno.get(), completo)
        return Optional.of(alumnoDTO)
    }
    private Optional<AlumnoConPlanesDTO> addOptionalPlan(Optional<Plan> optPlan, Alumno alumno, short curso) {
        if (optPlan.isEmpty()) {
            return Optional.empty()
        }

        AlumnoPlan assoc = new AlumnoPlan()
        assoc.alumno = alumno
        assoc.plan = optPlan.get()
        assoc.curso = curso

        alumnoPlanRepository.save(assoc)
        alumno.planAssoc.add(assoc)
        alumnoRepository.save(alumno)
        return Optional.of(new AlumnoConPlanesDTO(alumno))
    }
}
