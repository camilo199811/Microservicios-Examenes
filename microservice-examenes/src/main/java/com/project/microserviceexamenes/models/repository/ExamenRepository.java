package com.project.microserviceexamenes.models.repository;

import com.project.commonsexamenes.models.entity.Examen;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExamenRepository extends PagingAndSortingRepository<Examen,Long> {
    //Buscar examen por nombre
    @Query("select e from Examen e where e.nombre like %?1%")
    public List<Examen> findByNombre(String term);

    //examenes respondidos
    @Query("select e.id from Pregunta p  join p.examen e where  p.id in ?1 group by e.id")
    public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntasIds);
}
