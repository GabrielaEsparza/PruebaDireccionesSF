package com.prueba.nsmail.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class MunicipioId implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column (name = "clave")
    private String clave;


    @Column(name = "estado")
    private String estado;

    ///////////////////////////////

    public String getClave() {
        return clave;
    }//getClave

    public void setClave(String clave) {
        this.clave = clave;
    }//setClave

    public String getEstado() {
        return estado;
    }//getEstado

    public void setEstado(String estado) {
        this.estado = estado;
    }//setEstado


    ////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MunicipioId that = (MunicipioId) o;
        return Objects.equals(clave, that.clave) && Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clave, estado);
    }
}//MunicipioId
