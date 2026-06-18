package com.prueba.nsmail.repository;

import com.prueba.nsmail.model.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, String> {

    List<Colonia> findByCp(String cp);


}