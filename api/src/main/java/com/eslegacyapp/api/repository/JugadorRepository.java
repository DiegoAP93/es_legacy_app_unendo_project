package com.eslegacyapp.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eslegacyapp.api.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, String> {

    Optional<Jugador> findByCorreo(String correo);

}