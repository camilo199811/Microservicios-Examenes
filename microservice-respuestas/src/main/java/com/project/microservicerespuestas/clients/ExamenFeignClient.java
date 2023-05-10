package com.project.microservicerespuestas.clients;

import com.project.commonsexamenes.models.entity.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-examenes")
public interface ExamenFeignClient {

    @GetMapping({"/{id}"})
    public Examen obtenerExamenPorId(@PathVariable Long id);

    @GetMapping("/respondidos-por-preguntas")
    public List<Long> obtenerExamnesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntasIds);



}
