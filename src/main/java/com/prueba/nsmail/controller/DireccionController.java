package com.prueba.nsmail.controller;

import com.prueba.nsmail.dto.CodigoPostalResponseDTO;
import com.prueba.nsmail.dto.ValidarDireccionRequestDTO;
import com.prueba.nsmail.dto.ValidarDireccionResponseDTO;
import com.prueba.nsmail.model.Colonia;
import com.prueba.nsmail.model.Estado;
import com.prueba.nsmail.model.Localidad;
import com.prueba.nsmail.model.Municipio;
import com.prueba.nsmail.service.DireccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/direccion")
public class DireccionController {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }//Constructor

    // 1. Traer todos los estados
    @GetMapping("/estados")
    public List<Estado> obtenerEstados() {
        return direccionService.obtenerEstados();
    }//obtenerEstados

    // 2. Traer municipios de un estado
    @GetMapping("/estado/{estado}/municipios")
    public List<Municipio> obtenerMunicipios(@PathVariable String estado) {
        return direccionService.obtenerMunicipiosPorEstado(estado);
    }//obtenerMunicipios

    // 2. Traer localidades de un estado
    @GetMapping("/estado/{estado}/localidades")
    public List<Localidad> obtenerLocalidades(@PathVariable String estado) {
        return direccionService.obtenerLocalidadPorEstado(estado);
    }//obtenerLocalidades

    // 3. Resolver un código postal (estado, municipio, localidad, colonias)
    @GetMapping("/cp/{cp}")
    public ResponseEntity<CodigoPostalResponseDTO> obtenerDireccionPorCp(@PathVariable String cp) {
        Optional<CodigoPostalResponseDTO> resultado = direccionService.obtenerDireccionPorCp(cp);

        if (resultado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado.get());
    }//obtenerDireccionPorCp

    // 3b. Colonias de un código postal (usado aparte por el frontend)
    @GetMapping("/cp/{cp}/colonias")
    public List<Colonia> obtenerColonias(@PathVariable String cp) {
        return direccionService.obtenerColoniasPorCp(cp);
    }//obtenerColonias

    // 4. Validar dirección completa
    @PostMapping("/validar")
    public ValidarDireccionResponseDTO validarDireccion(@RequestBody ValidarDireccionRequestDTO request) {
        return direccionService.validarDireccion(request);
    }//validarDireccion

}//DireccionController