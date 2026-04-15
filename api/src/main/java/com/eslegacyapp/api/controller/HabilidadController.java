package com.eslegacyapp.api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eslegacyapp.api.model.Habilidad;
import com.eslegacyapp.api.repository.HabilidadRepository;

@RestController
@RequestMapping("/habilidades")
public class HabilidadController {

    private final HabilidadRepository habilidadRepository;

    public HabilidadController(HabilidadRepository habilidadRepository) {
        this.habilidadRepository = habilidadRepository;
    }

    @GetMapping
    public List<Habilidad> obtenerTodas() {
        return habilidadRepository.findAll();
    }

    @PostMapping
    public Habilidad guardar(@RequestBody Habilidad habilidad) {
        return habilidadRepository.save(habilidad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habilidad> obtenerPorId(@PathVariable int id) {
        Optional<Habilidad> h = habilidadRepository.findById(id);
        return h.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (habilidadRepository.existsById(id)) {
            habilidadRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
