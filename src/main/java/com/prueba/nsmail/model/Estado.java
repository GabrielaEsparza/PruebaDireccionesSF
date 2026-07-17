package com.prueba.nsmail.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estado")
public class Estado {

    @Id
    @Column(name = "clave")
    private String clave;

    @Column(name = "pais")
    private String pais;

    @Column (name = "nombre_estado")
    private String nombreEstado;

    public String getClave() {
        return clave;
    }//getClave

    public void setClave(String clave) {
        this.clave = clave;
    }//setClave

    public String getPais() {
        return pais;
    }//getPais

    public void setPais(String pais) {
        this.pais = pais;
    }//setPais

    public String getNombreEstado() {
        return nombreEstado;
    }//getNombreEstado

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }//setNombreEstado
}//Estado
