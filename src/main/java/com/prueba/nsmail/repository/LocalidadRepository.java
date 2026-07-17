package com.prueba.nsmail.repository;

import com.prueba.nsmail.model.Localidad;
import com.prueba.nsmail.model.LocalidadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalidadRepository extends JpaRepository<Localidad, LocalidadId> {
    List<Localidad> findByIdEstado(String estado);
}
