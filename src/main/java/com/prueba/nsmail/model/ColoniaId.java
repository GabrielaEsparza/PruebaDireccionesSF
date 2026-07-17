package com.prueba.nsmail.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ColoniaId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="clave")
    private String clave;

    @Column(name = "cp")
    private String cp;

    //////////////////////////

    public String getClave() {
        return clave;
    }//getClave

    public void setClave(String clave) {
        this.clave = clave;
    }//setClave

    public String getCp() {
        return cp;
    }//setCp

    public void setCp(String cp) {
        this.cp = cp;
    }//setCp

    /////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ColoniaId coloniaId = (ColoniaId) o;
        return Objects.equals(clave, coloniaId.clave) && Objects.equals(cp, coloniaId.cp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clave, cp);
    }
}//ColoniaId
