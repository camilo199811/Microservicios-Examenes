package com.project.cursos.cursosmicroservice.Service;

import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.project.commons.commonsmicroservice.services.CommonService;
import com.project.cursos.cursosmicroservice.models.entity.Curso;


import java.util.List;

public interface CursoService extends CommonService<Curso> {
    public Curso findCursoByAlumnoId(Long id);

    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumnos( Long alumnoId);

    public Iterable<Alumno> obtenerAlumnosPorCurso( Iterable<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);
}
