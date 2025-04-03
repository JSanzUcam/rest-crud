package edu.ucam.restcrud.controllers

import edu.ucam.restcrud.beans.dtos.PlanDTO
import edu.ucam.restcrud.services.PlanService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/planes")
class PlanController {
    @Autowired
    PlanService planService

    @PostMapping
    ResponseEntity<PlanDTO> create(@RequestBody PlanDTO planDto) {
        return ResponseEntity.ok(planService.create(planDto))
    }

    @GetMapping
    ResponseEntity<?> get(
        @RequestParam(name = "id", required = false) Integer id,
        @RequestParam(name = "alumnos", required = false) boolean incluirAlumnos
    ) {
        if (id != null) {
            PlanDTO plan = planService.get(id, incluirAlumnos)
            return ResponseEntity.ok(plan)
        } else {
            return ResponseEntity.ok(planService.getAll(incluirAlumnos))
        }
    }

    @PutMapping
    PlanDTO update(@RequestBody PlanDTO planDto) {
        return planService.update(planDto)
    }

    @DeleteMapping
    ResponseEntity<?> delete(
        @RequestParam("id") Integer id,
        @RequestParam("borrar_en") Short borrarEn
    ) {
        planService.logicDel(id, borrarEn)
        return ResponseEntity.ok("Eliminado correctamente")
    }
}
