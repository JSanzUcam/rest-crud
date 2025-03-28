package edu.ucam.restcrud.services

import edu.ucam.restcrud.beans.dtos.PlanAltaDTO
import edu.ucam.restcrud.beans.dtos.PlanAlumnosDTO
import edu.ucam.restcrud.beans.dtos.PlanDTO
import edu.ucam.restcrud.database.entities.Alumno
import edu.ucam.restcrud.database.entities.Plan
import edu.ucam.restcrud.database.repositories.AlumnoRepository
import edu.ucam.restcrud.database.repositories.PlanRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlanService {
    @Autowired
    PlanRepository planRepository
    @Autowired
    AlumnoRepository alumnoRepository

    PlanDTO create(PlanAltaDTO planDto) {
        Plan plan = new Plan()
        plan.nombre = planDto.nombre
        plan.tipo = planDto.tipo
        planRepository.save(plan)

        return new PlanDTO(plan)
    }

    List<PlanDTO> getAll(boolean alumnos = false, boolean completo = false) {
        List<Plan> planes = planRepository.findAll() as List<Plan>
        // Convertir Planes en DTOs con parámetros opcionales (alumnos? correos de los alumnos?)
        List<PlanDTO> planesDto = planes
            .stream()
            .map(plan -> {
                if (alumnos) {
                    return new PlanAlumnosDTO(plan, completo)
                } else {
                    return new PlanDTO(plan)
                }
            })
            .collect()
        return planesDto
    }

    Optional<PlanDTO> update(Integer id, PlanAltaDTO planDto) {
        Optional<Plan> planOpt = planRepository.findById(id)
        if (planOpt.isEmpty()) {
            return Optional.empty()
        }
        Plan plan = planOpt.get
        plan.nombre = planDto.nombre
        plan.tipo = planDto.tipo
        planRepository.save(plan)
        return Optional.of(new PlanDTO(plan))
    }

    boolean delete(Integer id) {
        Optional<Plan> plan = planRepository.findById(id)
        if (plan.isEmpty()) {
            return false
        }

        List<Alumno> alumnos = plan.get().alumnos
        for (Alumno alumno : alumnos) {
            alumno.planes.remove(plan.get())
        }

        planRepository.deleteById(id)
        return true
    }
}
