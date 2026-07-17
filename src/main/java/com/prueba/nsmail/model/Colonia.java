package com.prueba.nsmail.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "colonia")
public class Colonia {

    @EmbeddedId
    private ColoniaId id;

    @Column(name = "descripcion")
    private String descripcion;

    ///////////////////////////////////////

    public ColoniaId getId() {
        return id;
    }//ColoniaId

    public void setId(ColoniaId id) {
        this.id = id;
    }//setId

    public String getDescripcion() {
        return descripcion;
    }//getDescripcion

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }//setDescripcion

}//Colonia
