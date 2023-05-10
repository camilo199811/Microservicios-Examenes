package com.project.microservicerespuestas.service;

import com.project.commonsexamenes.models.entity.Examen;
import com.project.commonsexamenes.models.entity.Pregunta;
import com.project.microservicerespuestas.clients.ExamenFeignClient;
import com.project.microservicerespuestas.models.entity.Respuesta;
import com.project.microservicerespuestas.models.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestasServiceImpl implements RespuestasService {
    @Autowired
    private RespuestaRepository repository;

    @Autowired
    private ExamenFeignClient examenClient;
    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return repository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        /*Examen examen=examenClient.obtenerExamenPorId(examenId);
        List<Pregunta> preguntas=examen.getPreguntas();
        List<Long> preguntasIds=preguntas.stream().map(p->p.getId()).collect(Collectors.toList());
        List<Respuesta> respuestas= (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntaIds(alumnoId,preguntasIds);
        respuestas=respuestas.stream().map(r->{
            preguntas.forEach(p->{
                if(p.getId()==r.getPreguntaId()){
                    r.setPregunta(p);
                }
            });
            return r;
        }).collect(Collectors.toList());*/
        //Es un nuevo metodo porqeu ahora tenemos en Mongo tenemos toda la informacion
        List<Respuesta> respuestas= (List<Respuesta>) repository.findRespuestaByAlumnoByExamen(alumnoId,examenId);
        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {

        /*List<Respuesta> respuestasAlumno= (List<Respuesta>) repository.findByAlumnoId(alumnoId);
        List<Long> examenIds= Collections.emptyList();
        if(respuestasAlumno.size()>0) {
            //Comprobar que el alumno tiene respuestas de examenes
            List<Long> preguntaIds = respuestasAlumno.stream().map(r->
                r.getPreguntaId()
            ).collect(Collectors.toList());
            examenIds=examenClient.obtenerExamnesIdsPorPreguntasIdRespondidas(preguntaIds);
        }*/
        List<Respuesta> respuestasAlumno= (List<Respuesta>) repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
        List<Long> examenIds=respuestasAlumno.stream().map(r->r.getPregunta().getExamen().getId()).distinct().collect(Collectors.toList());
        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
        return repository.findByAlumnoId(alumnoId);
    }


}
