package com.project.microservicerespuestas.service;

import com.project.microservicerespuestas.models.entity.Respuesta;

public interface RespuestasService {

    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId,Long examenId);

    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);

    public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
