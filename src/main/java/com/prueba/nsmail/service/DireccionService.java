package com.prueba.nsmail.service;

import com.prueba.nsmail.dto.CodigoPostalResponseDTO;
import com.prueba.nsmail.model.*;
import com.prueba.nsmail.repository.EstadoRepository;
import com.prueba.nsmail.repository.CodigoPostalRepository;
import com.prueba.nsmail.repository.ColoniaRepository;
import com.prueba.nsmail.repository.LocalidadRepository;
import com.prueba.nsmail.repository.MunicipioRepository;
import org.springframework.stereotype.Service;
import com.prueba.nsmail.dto.ValidarDireccionRequestDTO;
import com.prueba.nsmail.dto.ValidarDireccionResponseDTO;

import java.util.List;
import java.util.Optional;


@Service
public class DireccionService {



    private final CodigoPostalRepository codigoPostalRepository;

    private final ColoniaRepository coloniaRepository;

    private final EstadoRepository estadoRepository;

    private final LocalidadRepository localidadRepository;

    private final MunicipioRepository municipioRepository;

    public DireccionService(EstadoRepository estadoRepository ,
                            CodigoPostalRepository codigoPostalRepository,
                            ColoniaRepository coloniaRepository,
                            LocalidadRepository localidadRepository,
                            MunicipioRepository municipioRepository){

        this.estadoRepository = estadoRepository;
        this.codigoPostalRepository = codigoPostalRepository;
        this.coloniaRepository = coloniaRepository;
        this.municipioRepository = municipioRepository;
        this.localidadRepository = localidadRepository;
    }//Constructor

    /////////////////////ESTADO (devovler todos los estados, no filtros):
    public List<Estado> obtenerEstados(){
        return estadoRepository.findAll();
    }//ObtenerEstados

    //////////////////////Municipio (devolver municipios, se filtra por el estado)
    public List<Municipio> obtenerMunicipiosPorEstado(String estado){
        return municipioRepository.findByIdEstado(estado);
    }//ObtenerMunicipiosPorEstado

    //////////////////////Localidad (devolver localidades, se filtra por estado)
    public List<Localidad> obtenerLocalidadPorEstado(String estado){
        return localidadRepository.findByIdEstado(estado);
    }//ObtenerLocalidadPorEstado

    /////////////////////Cp (aqui queremos que nos regrese el cp, pero puede no existir )
    public Optional<CodigoPostal> obtenerCodigoPostal(String cp){
        return codigoPostalRepository.findById(cp);
    }//obtenerCodigoPostal

    //////////////////////Colonia(devolver todas las colonias filtradas por CP)
    public List<Colonia> obtenerColoniasPorCp(String cp){
        return coloniaRepository.findByIdCp(cp);
    }//obtenerColoniasporCP

    ///////////Obtener direccion por CP
    public Optional<CodigoPostalResponseDTO> obtenerDireccionPorCp(String cp){
        Optional<CodigoPostal> resultado = obtenerCodigoPostal(cp);

        if (resultado.isEmpty()) {
            return Optional.empty();
        }

        CodigoPostal codigoPostal = resultado.get();
        String estado = codigoPostal.getEstado();
        String municipio = codigoPostal.getMunicipio();
        String localidad = codigoPostal.getLocalidad();
        List<Colonia> colonias = obtenerColoniasPorCp(cp);

        CodigoPostalResponseDTO dto = new CodigoPostalResponseDTO(estado, municipio, localidad, colonias);
        return Optional.of(dto);
    }//obtenerDireccionPorCp

    ///////////////////////////////////////////////////////////////////////////////////////////

    public ValidarDireccionResponseDTO validarDireccion(ValidarDireccionRequestDTO request) {

        ///////////Municipio pertenece al Estado
        MunicipioId municipioId = new MunicipioId();
        municipioId.setClave(request.getMunicipio());
        municipioId.setEstado(request.getEstado());

        Optional<Municipio> municipioEncontrado = municipioRepository.findById(municipioId);
        if (municipioEncontrado.isEmpty()) {
            return new ValidarDireccionResponseDTO(false, "El municipio no pertenece al estado seleccionado");
        }

        // 2. Localidad pertenece al Estado
        LocalidadId localidadId = new LocalidadId();
        localidadId.setClave(request.getLocalidad());
        localidadId.setEstado(request.getEstado());

        Optional<Localidad> localidadEncontrada = localidadRepository.findById(localidadId);
        if (localidadEncontrada.isEmpty()) {
            return new ValidarDireccionResponseDTO(false, "La localidad no pertenece al estado seleccionado");
        }

        ////////////////////Código Postal existe y coincide con el Estado
        Optional<CodigoPostal> cpEncontrado = obtenerCodigoPostal(request.getCp());
        if (cpEncontrado.isEmpty()) {
            return new ValidarDireccionResponseDTO(false, "El código postal no existe");
        }

        CodigoPostal codigoPostal = cpEncontrado.get();
        if (!codigoPostal.getEstado().equals(request.getEstado())) {
            return new ValidarDireccionResponseDTO(false, "El código postal no corresponde al estado seleccionado");
        }

        // El catalogo de CP puede no traer municipio/localidad para ciertos CP.
        // En ese caso no hay nada contra que comparar, el municipio/localidad ya
        // se valido arriba contra el catalogo del estado, asi que
        // se acepta. Cuando el catalogo si trae un valor, si debe coincidir
        if (codigoPostal.getMunicipio() != null
                && !codigoPostal.getMunicipio().equals(request.getMunicipio())) {
            return new ValidarDireccionResponseDTO(false, "El municipio no corresponde al código postal especificado");
        }

        if (codigoPostal.getLocalidad() != null
                && !codigoPostal.getLocalidad().equals(request.getLocalidad())) {
            return new ValidarDireccionResponseDTO(false, "La localidad no corresponde al código postal especificado");
        }

        ///////////////////Colonia pertenece al Código Postal
        List<Colonia> colonias = obtenerColoniasPorCp(request.getCp());

        // Si el CP no tiene colonias catalogadas, la colonia se captura a mano
        // en el frontend y no hay catálogo contra el cual validarla: solo se
        // exige que no venga vacía.
        if (colonias.isEmpty()) {
            if (request.getColonia() == null || request.getColonia().isBlank()) {
                return new ValidarDireccionResponseDTO(false, "La colonia es requerida");
            }
        } else {
            boolean coloniaValida = false;
            for (Colonia colonia : colonias) {
                if (colonia.getId().getClave().equals(request.getColonia())) {
                    coloniaValida = true;
                    break;
                }
            }

            if (!coloniaValida) {
                return new ValidarDireccionResponseDTO(false, "La colonia no pertenece al código postal especificado");
            }
        }

        // Si paso todas las comprobaciones:
        return new ValidarDireccionResponseDTO(true, "La dirección es válida");

    }//validarDireccion

}//DireccionService