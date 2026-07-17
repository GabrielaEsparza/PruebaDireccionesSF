package com.prueba.nsmail.dto;

public class ValidarDireccionResponseDTO {

    private boolean valida;
    private String mensaje;

    public ValidarDireccionResponseDTO(boolean valida, String mensaje) {
        this.valida = valida;
        this.mensaje = mensaje;
    }//Constructor

    ////////////////////////////

    public boolean isValida() {
        return valida;
    }//isValida

    public String getMensaje() {
        return mensaje;
    }//getMensaje
}//ValidarDireccionResponseDTO