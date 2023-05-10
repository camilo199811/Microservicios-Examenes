package com.project.microserviceexamenes.service;

import com.project.commons.commonsmicroservice.services.CommonServiceImpl;

import com.project.commonsexamenes.models.entity.Asignatura;
import com.project.commonsexamenes.models.entity.Examen;

import com.project.microserviceexamenes.models.repository.AsignaturaRepository;
import com.project.microserviceexamenes.models.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService{

    @Autowired
    private AsignaturaRepository asignaturaRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Examen> findByNombre(String term) {
        return repository.findByNombre(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Asignatura> findAllAsignaturas() {
        return  asignaturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntasIds) {
        return repository.findExamenesIdsConRespuestasByPreguntaIds(preguntasIds);
    }


}
