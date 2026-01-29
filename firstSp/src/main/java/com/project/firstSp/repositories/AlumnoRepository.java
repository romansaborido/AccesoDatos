package com.project.firstSp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.firstSp.entities.Alumno;

@Repository
public interface AlumnoRepository 
    extends JpaRepository<Alumno, Long> {
    // Métodos CRUD heredados
    // Query methods personalizados
    List<Alumno> findByNombre(String nombre);
}