package com.project.firstSp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Alumno {
    @Id 
    @GeneratedValue
    private Long id;
    private String nombre;
    private String email;
    
    // Getters y setters
    public Long getId() {
    	return this.id;
    }
    
    public String getNombre() {
    	return this.nombre;
    }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public String getEmail() {
    	return this.email;
    }
    
    public void setEmail(String email) {
    	this.email = email; 
    }
}
