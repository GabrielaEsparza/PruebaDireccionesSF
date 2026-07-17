package com.prueba.nsmail.repository;

import com.prueba.nsmail.model.Municipio;
import com.prueba.nsmail.model.MunicipioId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MunicipioRepository extends JpaRepository<Municipio, MunicipioId> {
    List<Municipio> findByIdEstado(String estado);
}
