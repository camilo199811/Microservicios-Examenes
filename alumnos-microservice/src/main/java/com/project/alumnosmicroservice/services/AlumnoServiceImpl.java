package com.project.alumnosmicroservice.services;


import com.project.alumnosmicroservice.client.CursoFeignClient;
import com.project.alumnosmicroservice.models.repository.AlumnoRepository;
import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.project.commons.commonsmicroservice.services.CommonServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

    @Autowired
    private CursoFeignClient clientCurso;
    @Override
    @Transactional(readOnly = true)
    public List<Alumno> finByNombreOrApellido(String term) {
        return repository.finByNombreOrApellido(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAllById(Iterable<Long> ids) {
        return  repository.findAllById(ids);
    }

    @Override
    public void eliminarCursoAlumnoPorId(Long id) {
        clientCurso.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.deleteById(id);
        this.eliminarCursoAlumnoPorId(id);
    }
   //Implementar metodo listar por Id
    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAll() {
        return repository.findAllByOrderByIdAsc();
    }
    //Implementar metodo listar por Id con paginacion
    @Override
    @Transactional(readOnly = true)
    public Page<Alumno> findAll(Pageable pageable) {
        return repository.findAllByOrderByIdAsc(pageable);
    }
}
