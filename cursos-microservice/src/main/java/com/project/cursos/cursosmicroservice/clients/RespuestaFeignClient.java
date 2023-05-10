package com.project.cursos.cursosmicroservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Se utiliza para conectar microservicios,cursos-respuestas
@FeignClient(name = "microservice-respuestas" )
public interface RespuestaFeignClient {
    //Parametros con los que se van a comunicar
    //Encontrar los examenes que ha respondio el alumno
    //se comunican por la misma ruta, revisar respuestacontroller
    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumnos(@PathVariable Long alumnoId);


}
