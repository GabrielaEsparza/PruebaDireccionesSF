package com.prueba.nsmail.repository;

import com.prueba.nsmail.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, String> {
    List<Municipio> findByEstado(String estado);
}