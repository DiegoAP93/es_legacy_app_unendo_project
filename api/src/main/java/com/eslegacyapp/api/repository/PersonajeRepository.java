package com.eslegacyapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eslegacyapp.api.model.Personaje;

public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {
	
}
