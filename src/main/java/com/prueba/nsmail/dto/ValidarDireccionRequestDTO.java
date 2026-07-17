package com.prueba.nsmail.dto;

public class ValidarDireccionRequestDTO {

    private String estado;
    private String municipio;
    private String localidad;
    private String cp;
    private String colonia;

    //////////////////////////

    public String getEstado() {
        return estado;
    }//getEstado

    public void setEstado(String estado) {
        this.estado = estado;
    }//setEstado

    public String getMunicipio() {
        return municipio;
    }//getMunicipio

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }//setMunicipio

    public String getLocalidad() {
        return localidad;
    }//getLocalidad

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }//setLocalidad

    public String getCp() {
        return cp;
    }//getCp

    public void setCp(String cp) {
        this.cp = cp;
    }//setCp

    public String getColonia() {
        return colonia;
    }//getColonia

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }//setColonia
}//ValidarDireccionRequestDTO