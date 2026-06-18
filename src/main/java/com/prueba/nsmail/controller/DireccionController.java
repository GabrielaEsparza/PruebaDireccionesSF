package com.prueba.nsmail.controller;

import com.prueba.nsmail.model.*;
import com.prueba.nsmail.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")


//lo dejaremos asi porque no son datos sensibles
@CrossOrigin(origins = "*")
public class DireccionController{

    @Autowired
    private DireccionService direccionService;

    @GetMapping("/estados")
    public List<Estado> getEstados(){
        return direccionService.getEstados();
    }// /Estados

    @GetMapping("/estado/{estado}/municipios")
    public List<Municipio> getMunicipios(@PathVariable String estado){
        return direccionService.getMunicipiosByEstado(estado);

    }//Municipios

    @GetMapping("/estado/{estado}/localidades")
    public List<Localidad> getLocalidad(@PathVariable String estado){
        return direccionService.getLocalidadByEstado(estado);
    }//getLocalidades

    @GetMapping("/cp/{cp}")
    public ResponseEntity<CodigoPostal> getCodigoPostal(@PathVariable String cp){
        Optional<CodigoPostal> codigoPostal = direccionService.getCodigoPostal(cp);
        return codigoPostal.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());

    }//getCodigoPostal

    @GetMapping("/cp/{cp}/colonias")
    public List<Colonia> getColonias(@PathVariable String cp){
        return direccionService.getColoniasByCp(cp);
    }//getColonias








}//DireccionController
