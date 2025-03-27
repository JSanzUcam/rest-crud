package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.CorreoAltaDTO
import edu.ucam.restcrud.beans.dtos.CorreoDTO
import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.database.entities.Correo
import edu.ucam.restcrud.database.repositories.AlumnoRepository
import edu.ucam.restcrud.database.repositories.CorreoRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CorreoService {
    @Autowired
    AlumnoRepository alumnoRepository
    @Autowired
    CorreoRepository correoRepository

    /**
     * Anade un correo a un alumno
     *
     * @param alumnoId
     * @param correoDto
     * @return <code>Optional&lt;CorreoDTO&gt;</code> con el correo si se ha anadido o vacio si el alumno no existe
     */
    Optional<CorreoDTO> addToAlumno(Integer alumnoId, @Valid CorreoAltaDTO correoDto) {
        Optional<Alumno> alumno = alumnoRepository.findById(alumnoId)
        // Si la ID no existe salimos directamente
        if (alumno.isEmpty()) {
            return Optional.empty()
        }

        // Creamos un correo a partir del DTO
        Correo correo = new Correo()
        correo.correo = correoDto.correo

        // Anadimos el correo al alumno y lo anadimos al repositorio
        alumno.get().correos.add(correo)
        correoRepository.save(correo)

        CorreoDTO dtoSalida = new CorreoDTO(correo)
        return Optional.of(dtoSalida)
    }

    /**
     * Devuelve todos los correos en la base de datos
     *
     * @return Iterable de Correos
     */
    Iterable<CorreoDTO> getAll() {
        List<Correo> correos = correoRepository.findAll() as List<Correo>
        return correos.stream().map(c -> {
            return new CorreoDTO(c)
        }).collect()
    }

    /**
     * Devuelve todos los correos de un alumno en un Optional.
     * El Optional estara vacio si el alumno no existe
     *
     * @param alumnoId
     * @return Optional de una Lista de CorreosDTO
     */
    Optional<List<CorreoDTO>> getByUserId(Integer alumnoId) {
        Optional<Alumno> alumno = alumnoRepository.findById(alumnoId)
        if (alumno.isEmpty()) {
            return Optional.empty()
        }

        List<CorreoDTO> correos = alumno.get()
            .getCorreos()
            .stream()
            .map(correo -> {
                return new CorreoDTO(correo)
            })
            .collect()

        return Optional.of(correos)
    }

    /**
     * Encuentra el correo por la ID y lo actualiza para cambiar su dirreccion de correo
     *
     * @param id la ID del correo
     * @param newCorreo el nuevo correo en forma de CorreoAltaDTO
     * @return Un Optional con el DTO de objeto modificado o nada si no se encontro un correo con esa ID
     */
    Optional<CorreoDTO> updateById(Integer id, @Valid CorreoAltaDTO newCorreo) {
        Optional<Correo> correoOpt = correoRepository.findById(id)
        if (correoOpt.isEmpty()) {
            return Optional.empty()
        }

        Correo correo = correoOpt.get()
        return Optional.of(update(correo, newCorreo))
    }

    /**
     * Encuentra el correo con la direccion especificada y le cambia la direccion
     *
     * @param correoOrig la direccion de correo original
     * @param newCorreo la nueva direccion de correo
     * @return Un Optional con el DTO de objeto modificado o nada si no se encontro un correo con esa direccion
     */
    Optional<CorreoDTO> updateByCorreo(String correoOrig, @Valid CorreoAltaDTO newCorreo) {
        Optional<Correo> correoOpt = correoRepository.findByCorreo(correoOrig)
        if (correoOpt.isEmpty()) {
            return Optional.empty()
        }

        Correo correo = correoOpt.get()
        return Optional.of(update(correo, newCorreo))
    }

    /**
     * Elimina un correo a partir de su ID
     *
     * @param id la ID del correo a eliminar
     * @return true si se ha eliminado, false si no se encontro el correo
     */
    boolean deleteById(Integer id) {
        if (correoRepository.findById(id).empty) {
            return false
        }
        correoRepository.deleteById(id)
        return true
    }

    /**
     * Elimina un correo a partir de su direccion de correo
     *
     * @param id la ID del correo a eliminar
     * @return true si se ha eliminado, false si no se encontro el correo
     */
    boolean deleteByCorreo(String correo) {
        if (correoRepository.findByCorreo(correo).empty) {
            return false
        }
        correoRepository.deleteByCorreo(correo)
        return true
    }


    /**
     * Cambia la direccion de correo a una entidad de Correo
     *
     * @param correo La entidad
     * @param newCorreo La nueva direccion de correo en un CorreoAltaDTO
     * @return el objeto actualizado en forma de DTO
     */
    private CorreoDTO update(Correo correo, CorreoAltaDTO newCorreo) {
        correo.setCorreo(newCorreo.correo)
        correoRepository.save(correo)

        CorreoDTO salidaDto = new CorreoDTO(correo)
        return salidaDto
    }
}
