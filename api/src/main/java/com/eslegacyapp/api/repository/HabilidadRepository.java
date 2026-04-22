package com.eslegacyapp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eslegacyapp.api.model.Habilidad;
import com.eslegacyapp.api.model.TipoHabilidad;

public interface HabilidadRepository extends JpaRepository<Habilidad, Integer> {
	
	List<Habilidad> findByCategoria(TipoHabilidad categoria);
}
