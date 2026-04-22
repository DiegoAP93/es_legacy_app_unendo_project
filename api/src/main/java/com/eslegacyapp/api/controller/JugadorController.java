package com.eslegacyapp.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.eslegacyapp.api.dto.LoginResponse;
import com.eslegacyapp.api.model.Jugador;
import com.eslegacyapp.api.model.Personaje;
import com.eslegacyapp.api.repository.JugadorRepository;
import com.eslegacyapp.api.repository.PersonajeRepository;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorRepository jugadorRepository;
    private final PersonajeRepository personajeRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public JugadorController(JugadorRepository jugadorRepository,
                             PersonajeRepository personajeRepository) {
        this.jugadorRepository = jugadorRepository;
        this.personajeRepository = personajeRepository;
    }

    /**Listar todos los jugadores**/
    @GetMapping
    public List<Jugador> obtenerTodos() {
        return jugadorRepository.findAll();
    }

    /**Obtener por nombre de usuario**/
    @GetMapping("/{username}")
    public ResponseEntity<Jugador> obtenerPorUsername(@PathVariable String username) {
        return jugadorRepository.findById(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**Registro de una nueva cuenta**/
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Jugador jugador) {

        if (jugadorRepository.existsById(jugador.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Este nombre de usuario ya ha sido utilizado.");
        }

        jugador.setPassword(encoder.encode(jugador.getPassword()));
        jugador.setActivo(true);
        jugador.setBloqueado(false);

        return ResponseEntity.ok(jugadorRepository.save(jugador));
    }

    /**Comprobación para login**/
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Jugador loginData) {

        Optional<Jugador> jugadorOpt = jugadorRepository.findById(loginData.getUsername());

        if (jugadorOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciales incorrectas.");
        }

        Jugador jugador = jugadorOpt.get();

        if (!jugador.isActivo()) {
            return ResponseEntity.status(403).body("Cuenta eliminada.");
        }

        if (jugador.isBloqueado()) {
            return ResponseEntity.status(403).body("Cuenta bloqueada.");
        }

        if (!encoder.matches(loginData.getPassword(), jugador.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales incorrectas.");
        }

        return ResponseEntity.ok(
            new LoginResponse(jugador.getUsername(), jugador.getRol())
        );
    }
    
    @PutMapping("/{username}")
    public ResponseEntity<?> editar(@PathVariable String username, @RequestBody Jugador cambios) {

        Optional<Jugador> jugadorOpt = jugadorRepository.findById(username);

        if (jugadorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jugador jugador = jugadorOpt.get();

        if (cambios.getNombreCompleto() != null) {
            jugador.setNombreCompleto(cambios.getNombreCompleto());
        }

        if (cambios.getCorreo() != null) {
            jugador.setCorreo(cambios.getCorreo());
        }

        if (cambios.getPassword() != null) {
            jugador.setPassword(encoder.encode(cambios.getPassword()));
        }

        jugadorRepository.save(jugador);

        return ResponseEntity.ok(jugador);
    }

    /**Desbloquear un personaje para el jugador en cuestión*/
    @PutMapping("/{username}/personajes/{idPersonaje}")
    public ResponseEntity<Jugador> desbloquearPersonaje(
            @PathVariable String username,
            @PathVariable int idPersonaje) {

        Optional<Jugador> jugadorOpt = jugadorRepository.findById(username);
        Optional<Personaje> personajeOpt = personajeRepository.findById(idPersonaje);

        if (jugadorOpt.isPresent() && personajeOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();

            jugador.addPersonaje(personajeOpt.get());
            jugadorRepository.save(jugador);

            return ResponseEntity.ok(jugador);
        }

        return ResponseEntity.notFound().build();
    }

    /**Bloquear una cuenta de usuario para no permitir acceso*/
    @PutMapping("/{username}/bloquear")
    public ResponseEntity<?> bloquearCuenta(@PathVariable String username) {

        Optional<Jugador> jugadorOpt = jugadorRepository.findById(username);

        if (jugadorOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();
            jugador.setBloqueado(true);
            jugadorRepository.save(jugador);
            return ResponseEntity.ok("Cuenta bloqueada.");
        }

        return ResponseEntity.notFound().build();
    }

    /**Eliminar la cuenta (no se puede recuperar), pero se mantiene un histórico*/
    @PutMapping("/{username}/eliminar")
    public ResponseEntity<?> eliminarCuenta(@PathVariable String username) {

        Optional<Jugador> jugadorOpt = jugadorRepository.findById(username);

        if (jugadorOpt.isPresent()) {
            Jugador jugador = jugadorOpt.get();
            jugador.setActivo(false);
            jugador.setFechaEliminacion(LocalDateTime.now());
            jugadorRepository.save(jugador);
            return ResponseEntity.ok("Cuenta eliminada correctamente.");
        }

        return ResponseEntity.notFound().build();
    }

}