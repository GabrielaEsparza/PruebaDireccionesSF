package com.prueba.nsmail.repository;

import com.prueba.nsmail.model.Colonia;
import com.prueba.nsmail.model.ColoniaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColoniaRepository extends JpaRepository<Colonia, ColoniaId> {
    List<Colonia> findByIdCp(String cp);
}
