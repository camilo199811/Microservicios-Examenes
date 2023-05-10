package com.project.commonsexamenes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asignaturas")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private  String nombre;
    @JsonIgnoreProperties(value = {"hijos","handler","hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura padre;
    @JsonIgnoreProperties(value = {"padre","handler","hibernateLazyInitializer"},allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "padre",cascade = CascadeType.ALL)
    private List<Asignatura> hijos;

    public Asignatura(){
        this.hijos=new ArrayList<>();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Asignatura getPadre() {
        return padre;
    }

    public void setPadre(Asignatura padre) {
        this.padre = padre;
    }

    public List<Asignatura> getHijos() {
        return hijos;
    }

    public void setHijos(List<Asignatura> hijos) {
        this.hijos = hijos;
    }
}
