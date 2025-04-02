package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import edu.ucam.restcrud.beans.dtos.CorreoAltaDTO
import edu.ucam.restcrud.beans.dtos.CorreoDTO
import edu.ucam.restcrud.database.entities.Correo
import edu.ucam.restcrud.database.repositories.CorreoRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CorreoService {
    @Autowired
    CorreoRepository correoRepository

    @Autowired
    AlumnoService alumnoService

    /**
     * Añade un correo a un alumno
     *
     * @param alumnoId
     * @param correoDto
     * @return <code>Optional&lt;CorreoDTO&gt;</code> con el correo si se ha añadido o vacío si el alumno no existe
     */
    Optional<CorreoDTO> addToAlumno(Integer alumnoId, @Valid CorreoAltaDTO correoDto) {
        if (!alumnoService.exists(alumnoId)) {
            return Optional.empty()
        }

        // Creamos un correo a partir del DTO
        Correo correo = new Correo()
        correo.correo = correoDto.correo

        // Añadimos el correo al alumno y lo añadimos al repositorio
        alumnoService.addCorreoToAlumno(alumnoId, correo)
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
     * El Optional estará vacío si el alumno no existe
     *
     * @param alumnoId
     * @return Optional de una Lista de CorreosDTO
     */
    Optional<List<CorreoDTO>> getByUserId(Integer alumnoId) {
        Optional<AlumnoDTO> alumno = alumnoService.get(alumnoId, true)
        if (alumno.isEmpty()) {
            return Optional.empty()
        }

        List<CorreoDTO> correos = alumno.get().getCorreos()
        return Optional.of(correos)
    }

    /**
     * Encuentra el correo por la ID y lo actualiza para cambiar su dirección de correo
     *
     * @param id la ID del correo
     * @param newCorreo el nuevo correo en forma de CorreoAltaDTO
     * @return Un Optional con el DTO de objeto modificado o nada si no se encontró un correo con esa ID
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
     * Elimina un correo a partir de su ID
     *
     * @param id la ID del correo a eliminar
     * @return true si se ha eliminado, false si no se encontró el correo
     */
    boolean delete(Integer id) {
        if (correoRepository.findById(id).empty) {
            return false
        }
        correoRepository.deleteById(id)
        return true
    }

    /**
     * Cambia la dirección de correo a una entidad de Correo
     *
     * @param correo La entidad
     * @param newCorreo La nueva dirección de correo en un CorreoAltaDTO
     * @return el objeto actualizado en forma de DTO
     */
    private CorreoDTO update(Correo correo, CorreoAltaDTO newCorreo) {
        correo.setCorreo(newCorreo.correo)
        correoRepository.save(correo)

        CorreoDTO salidaDto = new CorreoDTO(correo)
        return salidaDto
    }
}
