package edu.ucam.restcrud.services


import edu.ucam.restcrud.beans.dtos.PlanConAlumnosDTO
import edu.ucam.restcrud.beans.dtos.PlanDTO
import edu.ucam.restcrud.database.entities.AlumnoPlan
import edu.ucam.restcrud.database.entities.Plan
import edu.ucam.restcrud.database.repositories.AlumnoPlanRepository
import edu.ucam.restcrud.database.repositories.AlumnoRepository
import edu.ucam.restcrud.database.repositories.PlanRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.time.Year

@Service
class PlanService {
    @Autowired
    PlanRepository planRepository
    @Autowired
    AlumnoRepository alumnoRepository
    @Autowired
    AlumnoPlanRepository alumnoPlanRepository

    PlanDTO create(PlanDTO planDto) {
        Plan plan = new Plan()
        plan.nombre = planDto.nombre
        plan.tipo = planDto.tipo
        planRepository.save(plan)

        return new PlanDTO(plan)
    }

    Optional<PlanDTO> get(Integer id, boolean alumnos = false) {
        def now = Year.now().getValue()
        Optional<Plan> plan = planRepository.findByIdAndBorrarEnIsNullOrBorrarEnGreaterThan(id, (short)now)
        // Si no hay nada salir
        if (plan.isEmpty()) {
            return Optional.empty()
        }
        // Devolver un DTO u otro dependiendo de si queremos alumnos o no
        if (alumnos) {
            return Optional.of(new PlanConAlumnosDTO(plan.get()))
        } else {
            return Optional.of(new PlanDTO(plan.get()))
        }
    }

    List<PlanDTO> getAll(boolean alumnos = false) {
        def now = Year.now().getValue()
        List<Plan> planes = planRepository.findByBorrarEnIsNullOrBorrarEnGreaterThan((short)now)
        // Convertir Planes en DTOs con parámetros opcionales (alumnos? correos de los alumnos?)
        List<PlanDTO> planesDto = planes
            .stream()
            .map(plan -> {
                if (alumnos) {
                    return new PlanConAlumnosDTO(plan)
                } else {
                    return new PlanDTO(plan)
                }
            })
            .collect()
        return planesDto
    }

    Optional<PlanDTO> update(PlanDTO planDto) {
        Optional<Plan> planOpt = planRepository.findById(planDto.id)
        if (planOpt.isEmpty()) {
            return Optional.empty()
        }
        Plan plan = planOpt.get()
        plan.nombre = planDto.nombre
        plan.tipo = planDto.tipo
        planRepository.save(plan)
        return Optional.of(new PlanDTO(plan))
    }

    /**
     * Realiza un borrado lógico sobre un plan en concreto.
     * Esto hace que no sea posible que un alumno se matricule de un plan
     * que supuestamente está eliminado, mientras que los alumnos que ya
     * hayan cursado el plan sigan manteniendo su historial
     *
     * @param id ID del plan a eliminar
     * @param borrarEn Nuevo año de eliminación
     * @return false si no se encuentra el plan, true en caso contrario
     */
    boolean logicDel(Integer id, Short borrarEn) {
        Optional<Plan> plan = planRepository.findById(id)
        if (plan.isEmpty()) {
            return false
        }

        plan.get().setBorrarEn(borrarEn)
        planRepository.save(plan.get())
        return true
    }

    /**
     * Elimina el plan de la base de datos, eliminando registros de alumnos
     * teniendo este plan en el proceso.
     *
     * Usa logicDel() para realizar un borrado lógico
     *
     * @param id - ID del plan a eliminar
     * @return true si se elimina, false si no
     */
    boolean delete(Integer id) {
        Optional<Plan> plan = planRepository.findById(id)
        if (plan.isEmpty()) {
            return false
        }

        List<AlumnoPlan> alumnosPlanes = plan.get().getAlumnoAssoc()
        for (AlumnoPlan alumnoPlan : alumnosPlanes) {
            if (alumnoPlan.plan.id == id) {
                alumnoPlanRepository.delete(alumnoPlan)
            }
        }

        planRepository.delete(plan.get())
        return true
    }
}
