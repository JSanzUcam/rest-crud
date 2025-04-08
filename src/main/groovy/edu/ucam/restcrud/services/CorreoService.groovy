package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.AlumnoDTO
import edu.ucam.restcrud.beans.dtos.CorreoAltaDTO
import edu.ucam.restcrud.beans.dtos.CorreoDTO
import edu.ucam.restcrud.controllers.exceptions.NotFoundException
import edu.ucam.restcrud.controllers.exceptions.enums.EntityType
import edu.ucam.restcrud.database.universidad.entities.Correo
import edu.ucam.restcrud.database.universidad.repositories.CorreoRepository
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
     * @return CorreoDTO del correo nuevo
     */
    CorreoDTO addToAlumno(Integer alumnoId, @Valid CorreoAltaDTO correoDto) {
        if (!alumnoService.exists(alumnoId)) {
            throw new NotFoundException(EntityType.ALUMNO, alumnoId)
        }

        // Creamos un correo a partir del DTO
        Correo correo = new Correo()
        correo.correo = correoDto.correo

        // Añadimos el correo al alumno y lo añadimos al repositorio
        alumnoService.addCorreoToAlumno(alumnoId, correo)
        correoRepository.save(correo)

        return new CorreoDTO(correo)
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
     * Devuelve todos los correos de un alumno
     *
     * @param alumnoId
     * @return Lista de CorreosDTO
     */
    List<CorreoDTO> getByUserId(Integer alumnoId) {
        AlumnoDTO alumno = alumnoService.get(alumnoId, true)
        List<CorreoDTO> correos = alumno.getCorreos()
        return correos
    }

    /**
     * Encuentra el correo por la ID y lo actualiza para cambiar su dirección de correo
     *
     * @param id la ID del correo
     * @param newCorreo el nuevo correo en forma de CorreoAltaDTO
     * @return DTO de objeto modificado
     */
    CorreoDTO updateById(Integer id, @Valid CorreoAltaDTO newCorreo) {
        Optional<Correo> correoOpt = correoRepository.findById(id)
        if (correoOpt.isEmpty()) {
            throw new NotFoundException(EntityType.CORREO, id)
        }

        Correo correo = correoOpt.get()
        correo.setCorreo(newCorreo.correo)
        correoRepository.save(correo)

        return new CorreoDTO(correo)
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
}
