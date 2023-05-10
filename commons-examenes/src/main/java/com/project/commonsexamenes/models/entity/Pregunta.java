package com.project.commonsexamenes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    //Establecer relacion con examen
    //Muchas preguntas un examen
    //@join, establecer el nombre de la llave foranea
    @JsonIgnoreProperties(value = {"preguntas"} )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examen_id")
    private Examen examen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    //Metodo para eliminar preguntas, compara con el id para ver si existe y eliminarla
    @Override
    public boolean equals(Object obj){
        //compara si existe y lo elimina
        if(this==obj){
            return true;
        }
        //si el objeto no es instancia de preguntas es false
        if(!(obj instanceof Pregunta)){
            return false;
        }

        Pregunta a = (Pregunta) obj;
        return this.id !=null && this.id.equals(a.getId());
    }
}
