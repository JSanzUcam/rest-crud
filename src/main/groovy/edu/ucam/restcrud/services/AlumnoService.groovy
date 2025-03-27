package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.AlumnoAltaDTO
import edu.ucam.restcrud.beans.dtos.AlumnoCorreoDTO
import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.database.repositories.AlumnoRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AlumnoService {
    @Autowired
    AlumnoRepository alumnoRepository

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

        return new AlumnoDTO(newAlumno.id, newAlumno.dni, newAlumno.nombreCompleto, newAlumno.fechaNacimiento)
    }

    /**
     * Devuelve todos los datos de alumnos
     *
     * @param incluirCorreos booleano indicando si se deberian devolver los correos del alumno
     * @return Iterable con datos de alumnos
     */
    Iterable<AlumnoDTO> getAll(boolean incluirCorreos = false) {
        List<Alumno> alumnos = alumnoRepository.findAll() as List<Alumno>
        return alumnos.stream().map(a -> {
            if (incluirCorreos) {
                return new AlumnoCorreoDTO(a)
            } else {
                return new AlumnoDTO(a)
            }
        }).collect()
    }

    /**
     * Devuelve el alumno con la ID especificada
     *
     * @param id ID numerica del alumno
     * @return un valor opcional con el alumno encontrado o nada
     */
    Optional<AlumnoDTO> get(Integer id, boolean correo = false) {
        Optional<Alumno> alumno = alumnoRepository.findById(id)
        if (alumno.empty) {
            return Optional.empty()
        }

        AlumnoDTO alumnoDTO
        if (correo) {
            alumnoDTO = new AlumnoCorreoDTO(alumno.get())
        } else {
            alumnoDTO = new AlumnoDTO(alumno.get())
        }
        return Optional.of(alumnoDTO)
    }

    /**
     * Devuelve el alumno con el DNI especificado
     *
     * @param dni El DNI (unico) del alumno
     * @return un valor opcional con el alumno encontrado o nada
     */
    Optional<Alumno> get(String dni) {
        return alumnoRepository.findByDni(dni)
    }

    /**
     * Busca en la base de datos los alumnos cuyo nombre contenga el nombre indicado
     *
     * @param substring La seccion del nombre a buscar
     * @return List<Alumno> con los alumnos cuyo nombre contenga `substring`
     */
    List<Alumno> findWithNameContaining(String substring) {
        return alumnoRepository.findAllByNombreCompletoIgnoreCaseContaining(substring)
    }

    /**
     * Modifica los datos de un alumno en concreto usando su ID
     *
     * @param id Identificador unico del alumno
     * @param dto DTO de datos del alumno
     * @return true si se actualizan los datos, false en caso de que la ID no exista
     */
    boolean update(Integer id, @Valid AlumnoAltaDTO dto) {
        def optAlumno = alumnoRepository.findById(id)
        if (!optAlumno.empty) {
            def alumno = optAlumno.get()
            // Set datos
            alumno.setDni(dto.getDni())
            alumno.setNombreCompleto(dto.getNombre())
            alumno.setFechaNacimiento(dto.getFecha())
            alumnoRepository.save(alumno)

            return true
        }
        return false
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
     * DEBUG:
     * Borra todos los contenidos de la tabla
     */
    void nuke() {
        alumnoRepository.deleteAll()
    }
}
