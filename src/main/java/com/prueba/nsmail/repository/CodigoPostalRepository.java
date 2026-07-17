package com.prueba.nsmail.repository;
import com.prueba.nsmail.model.CodigoPostal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, String> {
}
