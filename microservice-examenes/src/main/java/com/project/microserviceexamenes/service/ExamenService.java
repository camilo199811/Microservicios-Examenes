package com.project.microserviceexamenes.service;

import com.project.commons.commonsmicroservice.services.CommonService;
import com.project.commonsexamenes.models.entity.Asignatura;
import com.project.commonsexamenes.models.entity.Examen;

import java.util.List;


public interface ExamenService extends CommonService<Examen> {
    public List<Examen> findByNombre(String term);

    public Iterable<Asignatura> findAllAsignaturas();

    public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntasIds);
}
