package com.prueba.nsmail.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "localidad")
public class Localidad {

    @EmbeddedId
    private LocalidadId id;

    @Column(name = "descripcion")
    private String descripcion;

    ////////////////////////

    public LocalidadId getId() {
        return id;
    }//getId

    public void setId(LocalidadId id) {
        this.id = id;
    }//setId

    public String getDescripcion() {
        return descripcion;
    }//getDescripcion

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }//setDescripcion
}//Localidad
