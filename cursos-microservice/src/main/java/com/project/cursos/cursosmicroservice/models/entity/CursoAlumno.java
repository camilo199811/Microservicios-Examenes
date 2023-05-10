package com.project.cursos.cursosmicroservice.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;

import javax.persistence.*;

//Esta entity sirve para establecer la relacion entre cursos y alumnos ya que estan en diferentes bases de datos
@Entity
@Table(name = "cursos_alumnos")
public class CursoAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "alumno_id",unique = true)
    private Long alumnoId;


    @JsonIgnoreProperties(value = {"cursoAlumnos"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    //Metodo para comparar si existe el alumno en el curso y eliminarlo
    @Override
    public boolean equals(Object obj){
        //compara si existe y lo elimina
        if(this==obj){
            return true;
        }
        //si el objeto no es instancia de alumnos es false
        if(!(obj instanceof CursoAlumno)){
            return false;
        }

        CursoAlumno a = (CursoAlumno) obj;
        return this.alumnoId !=null && this.alumnoId.equals(a.getAlumnoId());
    }
}
