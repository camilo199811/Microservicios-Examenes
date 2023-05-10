package com.project.commons.alumnos.commonsalumnos.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name="alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(name = "nombre")
    private String nombre;
    @NotEmpty
    private  String apellido;
    @NotEmpty
    @Email
    private String email;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    //Agregar foto
    @Lob
    @JsonIgnore
    private byte[] foto;


    @PrePersist
    public void prePersist(){
        this.createAt=new Date();
    }

    //Obtener foto a traves del arreglo de bytes
    //si la foto no es null retorna la imagen si lo es null
    public Integer getFotoHashCode(){
        return (this.foto != null) ? this.foto.hashCode() : null;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    //Metodo para comparar si existe el alumno en el curso y eliminarlo
    @Override
    public boolean equals(Object obj){
        //compara si existe y lo elimina
        if(this==obj){
            return true;
        }
        //si el objeto no es instancia de alumnos es false
        if(!(obj instanceof Alumno)){
            return false;
        }

        Alumno a = (Alumno) obj;
        return this.id !=null && this.id.equals(a.getId());
    }



}