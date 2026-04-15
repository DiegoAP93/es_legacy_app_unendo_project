package com.eslegacyapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eslegacyapp.api.model.Enemigo;

public interface EnemigoRepository extends JpaRepository<Enemigo, Integer> {
}
