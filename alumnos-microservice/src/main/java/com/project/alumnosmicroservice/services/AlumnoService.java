package com.project.alumnosmicroservice.services;


import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.project.commons.commonsmicroservice.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface AlumnoService extends CommonService<Alumno> {


    public List<Alumno> finByNombreOrApellido(String term);

    public Iterable<Alumno> findAllById(Iterable<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);


}
