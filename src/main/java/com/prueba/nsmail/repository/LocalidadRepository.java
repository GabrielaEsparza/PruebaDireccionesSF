package com.prueba.nsmail.repository;

import com.prueba.nsmail.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, String> {


    List<Localidad>findByEstado(String estado);



}