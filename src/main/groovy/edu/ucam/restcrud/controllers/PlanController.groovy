package edu.ucam.restcrud.controllers


import edu.ucam.restcrud.beans.dtos.PlanDTO
import edu.ucam.restcrud.services.PlanService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/api/planes")
class PlanController {
    @Autowired
    PlanService planService

    @PostMapping
    @ResponseBody ResponseEntity<PlanDTO> create(@RequestBody PlanDTO planDto) {
        return ResponseEntity.ok(planService.create(planDto))
    }

    @GetMapping
    @ResponseBody List<PlanDTO> getAll(
        @RequestParam(name = "alumnos", required = false) boolean incluirAlumnos,
        @RequestParam(name = "completo", required = false) boolean alumnosCompletos
    ) {
        return planService.getAll(incluirAlumnos, alumnosCompletos)
    }

    @PutMapping
    @ResponseBody ResponseEntity<?> update(@RequestBody PlanDTO planDto) {
        Optional<PlanDTO> planOpt = planService.update(planDto)
        if (planOpt.isEmpty()) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(planOpt.get())
    }

    @DeleteMapping
    @ResponseBody ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        if (!planService.delete(id)) {
            return ResponseEntity.notFound().build()
        } else {
            return ResponseEntity.ok().build()
        }
    }
}
