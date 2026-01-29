package com.project.firstSp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.firstSp.entities.Alumno;


import com.project.firstSp.repositories.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
 
	@Autowired
	private AlumnoRepository repo;
 
	@GetMapping
	public List<Alumno> listar() {
		return repo.findAll();
	}
 
	@PostMapping
	public Alumno crear(@RequestBody Alumno alumno) {
		return repo.save(alumno);
	}
	
	@PutMapping("/{id}")
	public Alumno update(@PathVariable Long id, @RequestBody Alumno alumno) {
	    Optional<Alumno> alumnoExistente = repo.findById(id);

	    if (alumnoExistente.isPresent()) {
	        Alumno a = alumnoExistente.get(); // obtenemos el Alumno real
	        a.setNombre(alumno.getNombre());
	        a.setEmail(alumno.getEmail());
	        return repo.save(a);
	    } else {
	        // si no existe, puedes lanzar excepción o crear uno nuevo
	        throw new RuntimeException("Alumno no encontrado");
	    }
	}
	
	

}