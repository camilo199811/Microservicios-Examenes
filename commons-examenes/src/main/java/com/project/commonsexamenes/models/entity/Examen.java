package com.project.commonsexamenes.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "examenes")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 4,max = 40)
    private String nombre;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;

    //Establecer relacion bidireccional
    //Un examen muchas preguntas
    //mappedBy, establecer relacion con el examen de entity preguntas, es el nombre que se le da Examen examen <-
    //cascade, al eliminar un examen se eliminan sus preguntas
    //orphanRemoval, cualquier pregunta que no este asociada a un examen la va a eliminar
    //JsonIgnoreProperties, ignorar atributos de la clase
    //allowsetters, permita agregar examenes a las preguntas
    @JsonIgnoreProperties(value = {"examen"},allowSetters = true)
    @OneToMany(mappedBy ="examen" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Pregunta> preguntas;

    //Estableciendo relacion examen-asignatura
    //Muchos examenes asociados a una asignatura
    @JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asignaturaPadre;
    @JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asignaturaHija;

    //Saber si el examen ya tuvo respuesta por parte del alumno
    @Transient
    private boolean respondido;

    public Examen(){
        this.preguntas=new ArrayList<>();
    }

    @PrePersist
    public void prePersist(){
        this.createAt=new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Asignatura getAsignaturaPadre() {
        return asignaturaPadre;
    }

    public void setAsignaturaPadre(Asignatura asignaturaPadre) {
        this.asignaturaPadre = asignaturaPadre;
    }

    public Asignatura getAsignaturaHija() {
        return asignaturaHija;
    }

    public void setAsignaturaHija(Asignatura asignaturaHija) {
        this.asignaturaHija = asignaturaHija;
    }

    public boolean isRespondido() {
        return respondido;
    }

    public void setRespondido(boolean respondido) {
        this.respondido = respondido;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
    //Se modifica el metodo set para que se asignen las preguntas al examen sino su llave foranea queda en null
    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas.clear();//si elimino preguntas de la lista se va a reiniciar la lista para actualizar
        preguntas.forEach(
            this::addPreguntas
        );
    }

    //Agregar preguntas al examen
    public void addPreguntas(Pregunta pregunta) {
        this.preguntas.add(pregunta);
        //Aca se establece la relacion inversa con examenes, el this es el examen
        pregunta.setExamen(this);
    }

    //Eliminar preguntas al examen
    public void removePreguntas(Pregunta pregunta) {
        this.preguntas.remove(pregunta);
        //Aca se establece la relacion inversa con examenes, el this es el examen
        pregunta.setExamen(null);
    }

    //Metodo para comparar si existe el examen en el curso y eliminarlo
    @Override
    public boolean equals(Object obj){
        //compara si existe y lo elimina
        if(this==obj){
            return true;
        }
        //si el objeto no es instancia de examen es false
        if(!(obj instanceof Examen)){
            return false;
        }

        Examen a = (Examen) obj;
        return this.id !=null && this.id.equals(a.getId());
    }
}
