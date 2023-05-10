package com.project.cursos.cursosmicroservice.Service;

import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.project.commons.commonsmicroservice.services.CommonServiceImpl;
import com.project.cursos.cursosmicroservice.clients.AlumnoFeignClient;
import com.project.cursos.cursosmicroservice.clients.RespuestaFeignClient;
import com.project.cursos.cursosmicroservice.models.entity.Curso;
import com.project.cursos.cursosmicroservice.models.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

    @Autowired
    private RespuestaFeignClient client;

    @Autowired
    AlumnoFeignClient clientAlumno;
    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumnos(Long alumnoId) {
        return client.obtenerExamenesIdsConRespuestasAlumnos(alumnoId);
    }

    @Override
    public Iterable<Alumno> obtenerAlumnosPorCurso(Iterable<Long> ids) {
        return clientAlumno.obtenerAlumnosPorCurso((List<Long>) ids);
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        repository.eliminarCursoAlumnoPorId(id);
    }
}
