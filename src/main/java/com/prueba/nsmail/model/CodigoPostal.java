package com.prueba.nsmail.model;

import jakarta.persistence.*;

@Entity
@Table(name = "codigo_postal")

public class CodigoPostal {
    
    @Id
    @Column(name = "cp")
    private String cp;

    @Column(name = "estado")
    private String estado;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "localidad")
    private String localidad;

    public String getCp() {return cp;}
    public void setCp(String cp){this.cp = cp;}

    public String getEstado(){return estado; }
    public void setEstado(String estado){this.estado = estado;}

    public String getMunicipio(){return municipio;}
    public void setMunicipio(String municipio){this.municipio = municipio;}

    public String getLocalidad(){return localidad;}
    public void setLocalidad(String localidad){this.localidad = localidad;}


}