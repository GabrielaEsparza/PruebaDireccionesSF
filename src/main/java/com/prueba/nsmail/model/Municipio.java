package com.prueba.nsmail.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "municipio")
public class Municipio {

    @EmbeddedId
    private MunicipioId id;

    @Column(name = "descripcion")
    private String descripcion;

    public MunicipioId getId() {
        return id;
    }//getId

    public void setId(MunicipioId id) {
       this.id = id;
    }//SetId

    public String getDescripcion() {
        return descripcion;
    }//getDescripcion

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }//setDecripcion
}//Municipio

