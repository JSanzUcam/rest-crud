package edu.ucam.restcrud.beans.dtos

import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.database.entities.Correo

class AlumnoCorreoDTO extends AlumnoDTO {
    List<Correo> correos

    AlumnoCorreoDTO(Integer id, String dni, String nombre, Date fecha) {
        super(id, dni, nombre, fecha)
    }
    AlumnoCorreoDTO(Integer id, String dni, String nombre, Date fecha, List<Correo> correos) {
        super(id, dni, nombre, fecha)
        this.correos = correos
    }
    AlumnoCorreoDTO(Alumno a) {
        super(a)
        this.correos = a.correos
    }
}