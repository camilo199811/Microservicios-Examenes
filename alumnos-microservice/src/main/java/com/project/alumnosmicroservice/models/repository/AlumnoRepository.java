package com.project.alumnosmicroservice.models.repository;


import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
//Ya no utilizamos crudrepository porqque con pagin se hace la paginacion
public interface AlumnoRepository extends PagingAndSortingRepository<Alumno,Long> {
    //Buscar usuario con like,upper pasar  a mayuscula
    @Query("select a from Alumno a where upper(a.nombre) like upper(concat('%',?1,'%')) or upper(a.apellido) like upper(concat('%',?1,'%'))" )
    public List<Alumno> finByNombreOrApellido(String term);

    //consulta para ordenar por id en Angular
    public Iterable<Alumno> findAllByOrderByIdAsc();

    //consulta para ordenar por id en Angular con paginacion
    public Page<Alumno> findAllByOrderByIdAsc(Pageable pageable);
}
