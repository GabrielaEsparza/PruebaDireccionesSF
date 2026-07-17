package com.prueba.nsmail.dto;

import com.prueba.nsmail.model.Colonia;
import java.util.List;

public class CodigoPostalResponseDTO {

    private String estado;
    private String municipio;
    private String localidad;
    private List<Colonia> colonias;

    public CodigoPostalResponseDTO(String estado, String municipio, String localidad, List<Colonia> colonias) {
        this.estado = estado;
        this.municipio = municipio;
        this.localidad = localidad;
        this.colonias = colonias;
    }//Constructor
///////////////////////////////////////

    public String getEstado() {
        return estado;
    }//getEstado

    public String getMunicipio() {
        return municipio;
    }//getMunicipio

    public String getLocalidad() {
        return localidad;
    }//getLocalidad

    public List<Colonia> getColonias() {
        return colonias;
    }//getColonias

}//CodigoPostalResponseDTO