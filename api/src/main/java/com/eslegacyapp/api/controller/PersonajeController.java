package com.eslegacyapp.api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eslegacyapp.api.model.Habilidad;
import com.eslegacyapp.api.model.Inventario;
import com.eslegacyapp.api.model.Objeto;
import com.eslegacyapp.api.model.Personaje;
import com.eslegacyapp.api.repository.HabilidadRepository;
import com.eslegacyapp.api.repository.PersonajeRepository;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {

    private final PersonajeRepository personajeRepository;
    private final HabilidadRepository habilidadRepository;

    public PersonajeController(PersonajeRepository personajeRepository,
    		HabilidadRepository habilidadRepository) {
        this.personajeRepository = personajeRepository;
        this.habilidadRepository = habilidadRepository;
    }

    @GetMapping
    public List<Personaje> obtenerTodos() {
        return personajeRepository.findAll();
    }
    
    @PostMapping
    public Personaje guardar(@RequestBody Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Personaje> obtenerPorId(@PathVariable int id) {
        Optional<Personaje> p = personajeRepository.findById(id);
        return p.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personaje> actualizar(@PathVariable int id, @RequestBody Personaje cambios) {
        return personajeRepository.findById(id)
            .map(personaje -> {
                if (cambios.getClase() != null) personaje.setClase(cambios.getClase());
                if (cambios.getRareza() != null) personaje.setRareza(cambios.getRareza());
                if (cambios.getHistoria() != null) personaje.setHistoria(cambios.getHistoria());
                if (cambios.getAtaqueBasico() != null) personaje.setAtaqueBasico(cambios.getAtaqueBasico());
                if (cambios.getPuntosVida() != null) personaje.setPuntosVida(cambios.getPuntosVida());
                if (cambios.getIniciativa() != null) personaje.setIniciativa(cambios.getIniciativa());
                if (cambios.getOrigen() != null) personaje.setOrigen(cambios.getOrigen());
                if (cambios.getArquetipo() != null) personaje.setArquetipo(cambios.getArquetipo());

                Personaje actualizado = personajeRepository.save(personaje);
                return ResponseEntity.ok(actualizado);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (personajeRepository.existsById(id)) {
            personajeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{idPersonaje}/habilidades/{idHabilidad}")
    public ResponseEntity<Personaje> asignarHabilidad(
            @PathVariable int idPersonaje,
            @PathVariable int idHabilidad) {

        Optional<Personaje> personajeOpt = personajeRepository.findById(idPersonaje);
        Optional<Habilidad> habilidadOpt = habilidadRepository.findById(idHabilidad);

        if (personajeOpt.isPresent() && habilidadOpt.isPresent()) {
            Personaje personaje = personajeOpt.get();
            Habilidad habilidad = habilidadOpt.get();

            personaje.addHabilidad(habilidad);
            personajeRepository.save(personaje);

            return ResponseEntity.ok(personaje);
        }

        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id}/inventario")
    public ResponseEntity<List<Inventario>> obtenerInventario(@PathVariable int id) {
        return personajeRepository.findById(id)
                .map(personaje -> ResponseEntity.ok(personaje.getInventario()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/inventario")
    public ResponseEntity<Inventario> agregarObjeto(@PathVariable int id, @RequestBody Objeto objeto) {
        return personajeRepository.findById(id)
            .map(personaje -> {
                Inventario inv = new Inventario();
                inv.setPersonaje(personaje);
                inv.setObjeto(objeto);
                inv.setCantidad(1);
                inv.setEquipado(false);
                personaje.getInventario().add(inv);
                personajeRepository.save(personaje);
                return ResponseEntity.ok(inv);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/inventario/{inventarioId}/equipar")
    public ResponseEntity<Inventario> equiparObjeto(
            @PathVariable int id,
            @PathVariable int inventarioId) {

        Optional<Personaje> personajeOpt = personajeRepository.findById(id);

        if (personajeOpt.isEmpty()) return ResponseEntity.notFound().build();

        Personaje personaje = personajeOpt.get();

        Inventario inv = personaje.getInventario().stream()
                .filter(i -> i.getId() == inventarioId)
                .findFirst()
                .orElse(null);

        if (inv == null) return ResponseEntity.notFound().build();

        Objeto obj = inv.getObjeto();
        String tipo = obj.getCategoria();

        long equipados = personaje.getInventario().stream()
                .filter(i -> i.isEquipado() && i.getObjeto().getCategoria().equals(tipo))
                .count();

        int maxEquip = switch (tipo) {
            case "arma" -> 1;
            case "armadura" -> 1;
            case "accesorio" -> 2;
            default -> Integer.MAX_VALUE;
        };

        if (!inv.isEquipado() && equipados >= maxEquip) {
            return ResponseEntity.badRequest()
                .body(null);
        }

        inv.setEquipado(!inv.isEquipado());
        personajeRepository.save(personaje);

        return ResponseEntity.ok(inv);
    }
    
    @DeleteMapping("/{id}/inventario/{inventarioId}")
    public ResponseEntity<Void> quitarObjeto(
            @PathVariable int id,
            @PathVariable int inventarioId) {

        Optional<Personaje> personajeOpt = personajeRepository.findById(id);

        if (personajeOpt.isEmpty()) return ResponseEntity.notFound().build();

        Personaje personaje = personajeOpt.get();
        boolean removed = personaje.getInventario().removeIf(i -> i.getId() == inventarioId);

        if (removed) {
            personajeRepository.save(personaje);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
