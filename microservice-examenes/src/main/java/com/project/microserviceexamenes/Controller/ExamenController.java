package com.project.microserviceexamenes.Controller;

import com.project.commons.commonsmicroservice.controllers.CommonController;
import com.project.commonsexamenes.models.entity.Examen;

import com.project.commonsexamenes.models.entity.Pregunta;
import com.project.microserviceexamenes.service.ExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?> obtenerExamnesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntasIds){
        return ResponseEntity.ok().body(service.findExamenesIdsConRespuestasByPreguntaIds(preguntasIds));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validar(result);

        }

        //Obtener el examen por id
        Optional<Examen> o = service.findById(id);
        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Examen examenDb = o.get();
        examenDb.setNombre(examen.getNombre());

        //Obtenemos las preguntas del examen
        List<Pregunta> eliminadas = new ArrayList<>();

        examenDb.getPreguntas().forEach(pdb -> {
            //pdb- hace referencia a las preguntas nuevas, compara si ya existen en el examen sino las agrega para eliminarlas y hacer un reset con las nuevas preguntas
            if (!examen.getPreguntas().contains(pdb)) {
                eliminadas.add(pdb);
            }
        });
        //Se eliminan las preguntas del examen
        eliminadas.forEach(
                examenDb::removePreguntas
        );

        //Se agregan las preguntas del examen y se mantienen las que no fueron eliminadas
        examenDb.setPreguntas(examen.getPreguntas());
        examenDb.setAsignaturaHija(examen.getAsignaturaHija());
        examenDb.setAsignaturaPadre(examen.getAsignaturaPadre());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
    }

    //Buscar examen por nombre
    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(service.findByNombre(term));
    }

    //Ver rasignatuas
    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas(){
        return ResponseEntity.ok(service.findAllAsignaturas());
    }
}


