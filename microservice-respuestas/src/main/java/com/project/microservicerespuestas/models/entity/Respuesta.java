package com.project.microservicerespuestas.models.entity;

import com.project.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.project.commonsexamenes.models.entity.Pregunta;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
//Mongodb es @Document
@Document(collection = "respuestas")
public class Respuesta {

    @Id

    private String id;

    private String texto;

    //Añadiendo relacion entre pregunta y alumno
    //Muchas respuestas un alumno
    //@ManyToOne(fetch = FetchType.LAZY)
    //@Transient
    private Alumno alumno;

    private Long alumnoId;
    //Añadiendo relacion entre pregunta y alumno
    //una respuesta una pregunta
    //@Transient
    private Pregunta pregunta;

    @Field
    private Long preguntaId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }


}
