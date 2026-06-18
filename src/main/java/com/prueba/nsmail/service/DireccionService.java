package com.prueba.nsmail.service;

import com.prueba.nsmail.model.*;
import com.prueba.nsmail.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DireccionService{


    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

    @Autowired
    private ColoniaRepository coloniaRepository;


    public List<Estado> getEstados(){
        return estadoRepository.findAll();


    }//getEstados

    public List<Municipio> getMunicipiosByEstado(String estado){
        return municipioRepository.findByEstado(estado);
    }//getMunicipiosByEstado

    public List<Localidad> getLocalidadByEstado(String estado){
        return localidadRepository.findByEstado(estado);
    }//getLocalidadByEstado

    public Optional<CodigoPostal> getCodigoPostal(String cp){
        return codigoPostalRepository.findById(cp);
    }//getCodigoPostal

    public List<Colonia> getColoniasByCp(String cp){
        return coloniaRepository.findByCp(cp);
    }//getColoniaByCp






}//DireccionService