package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.AlumnoAltaDTO
import edu.ucam.restcrud.beans.dtos.AlumnoFullDTO
import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.database.entities.Correo
import edu.ucam.restcrud.database.entities.Plan
import edu.ucam.restcrud.database.repositories.AlumnoRepository
import edu.ucam.restcrud.database.repositories.PlanRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AlumnoService {
    @Autowired
    AlumnoRepository alumnoRepository

    // Esto hace falta para poder anadir Planes (Entidad) a Alumnos (Entidad)
    @Autowired
    PlanRepository planRepository

    /**
     * Anade un alumno nuevo a la base de datos
     *
     * @param alumno DTO con datos del alumno sin la ID
     * @return true si se ha creado, false en caso de error
     */
    AlumnoDTO create(@Valid AlumnoAltaDTO alumno) {
        Alumno newAlumno = new Alumno()
        newAlumno.setDni(alumno.getDni())
        newAlumno.setNombreCompleto(alumno.getNombre())
        newAlumno.setFechaNacimiento(alumno.getFecha())
        alumnoRepository.save(newAlumno)

        return new AlumnoDTO(newAlumno)
    }

    /**
     * Devuelve todos los datos de alumnos
     *
     * @param completo booleano indicando si se deberian devolver TODOS los datos del alumno
     * @return Iterable con datos de alumnos
     */
    Iterable<AlumnoDTO> getAll(boolean completo = false) {
        List<Alumno> alumnos = alumnoRepository.findAll() as List<Alumno>
        return alumnos.stream().map(a -> {
            if (completo) {
                return new AlumnoFullDTO(a)
            } else {
                return new AlumnoDTO(a)
            }
        }).collect()
    }

    /**
     * Devuelve el alumno con la ID especificada
     *
     * @param id ID numerica del alumno
     * @param completo Booleano indicando si se deben mostrar todos los datos
     * @return un valor opcional con el alumno encontrado o nada
     */
    Optional<AlumnoDTO> get(Integer id, boolean completo = false) {
        Optional<Alumno> alumno = alumnoRepository.findById(id)
        return optionalDtoFromOptionalAlumno(alumno, completo)
    }

    /**
     * Devuelve el alumno con el DNI especificado
     *
     * @param dni El DNI (unico) del alumno
     * @param completo Booleano indicando si se deben mostrar todos los datos
     * @return un valor opcional con el alumno encontrado o nada
     */
    Optional<AlumnoDTO> get(String dni, boolean completo = false) {
        Optional<Alumno> alumno = alumnoRepository.findByDni(dni)
        return optionalDtoFromOptionalAlumno(alumno, completo)
    }

    /**
     * Busca en la base de datos los alumnos cuyo nombre contenga el nombre indicado
     *
     * @param substring La seccion del nombre a buscar
     * @param completo Booleano indicando si se deben mostrar todos los datos
     * @return List<AlumnoDTO> con los alumnos cuyo nombre contenga `substring`
     */
    List<AlumnoDTO> findWithNameContaining(String substring, boolean completo = false) {
        List<Alumno> alumnos = alumnoRepository.findAllByNombreCompletoIgnoreCaseContaining(substring) as List<Alumno>
        return alumnos
            .stream()
            .map(alumno -> {
                if (completo) {
                    return new AlumnoFullDTO(alumno)
                } else {
                    return new AlumnoDTO(alumno)
                }
            })
            .collect()
    }

    /**
     * Modifica los datos de un alumno en concreto usando su ID
     *
     * @param id Identificador unico del alumno
     * @param dto DTO de datos del alumno
     * @return true si se actualizan los datos, false en caso de que la ID no exista
     */
    Optional<AlumnoDTO> update(Integer id, @Valid AlumnoAltaDTO dto) {
        def optAlumno = alumnoRepository.findById(id)
        if (!optAlumno.empty) {
            def alumno = optAlumno.get()
            // Set datos
            alumno.setDni(dto.getDni())
            alumno.setNombreCompleto(dto.getNombre())
            alumno.setFechaNacimiento(dto.getFecha())
            alumnoRepository.save(alumno)

            return Optional.of(new AlumnoDTO(alumno))
        }
        return Optional.empty()
    }

    Optional<AlumnoFullDTO> addPlan(Integer id, Integer plan) {
        Optional<Alumno> optAlumno = alumnoRepository.findById(id)
        if (optAlumno.isEmpty()) {
            return Optional.empty()
        }
        Optional<Plan> optPlan = planRepository.findById(plan)
        return addOptionalPlan(optPlan, optAlumno.get())
    }
    Optional<AlumnoFullDTO> addPlan(Integer id, String plan) {
        Optional<Alumno> optAlumno = alumnoRepository.findById(id)
        if (optAlumno.isEmpty()) {
            return Optional.empty()
        }
        Optional<Plan> optPlan = planRepository.findByNombre(plan)
        return addOptionalPlan(optPlan, optAlumno.get())
    }

    /**
     * Elimina un alumno de la base de datos a partir de su ID
     *
     * @param id Identificador unico del alumno
     * @return true si se ha eliminado el alumno, false si no se ha eliminado
     */
    boolean delete(Integer id) {
        if (alumnoRepository.findById(id).empty) {
            return false
        }
        alumnoRepository.deleteById(id)
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
     * Anade un nuevo correo al alumno. Esto no guarda el correo
     * en la base de datos
     *
     * @param id ID del alumno
     * @param correo Entidad Correo a anadir
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


    private static Optional<AlumnoDTO> optionalDtoFromOptionalAlumno(Optional<Alumno> alumno, boolean completo = false) {
        if (alumno.empty) {
            return Optional.empty()
        }

        AlumnoDTO alumnoDTO
        if (completo) {
            alumnoDTO = new AlumnoFullDTO(alumno.get())
        } else {
            alumnoDTO = new AlumnoDTO(alumno.get())
        }
        return Optional.of(alumnoDTO)
    }
    private Optional<AlumnoFullDTO> addOptionalPlan(Optional<Plan> optPlan, Alumno alumno) {
        if (optPlan.isEmpty()) {
            return Optional.empty()
        }
        // No queremos anadir varias veces el mismo plan
        if (alumno.planes.contains(optPlan.get())) {
            return Optional.of(new AlumnoFullDTO(alumno))
        }

        alumno.planes.add(optPlan.get())
        alumnoRepository.save(alumno)
        return Optional.of(new AlumnoFullDTO(alumno))
    }
}
