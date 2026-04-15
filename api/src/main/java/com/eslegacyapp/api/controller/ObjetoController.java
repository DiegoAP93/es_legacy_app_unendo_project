package com.eslegacyapp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eslegacyapp.api.model.Objeto;
import com.eslegacyapp.api.repository.ObjetoRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/objetos")
public class ObjetoController {

    @Autowired
    private ObjetoRepository objetoRepository;

    @GetMapping
    public List<Objeto> getAll() {
        return objetoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Objeto> getById(@PathVariable int id) {
        Optional<Objeto> objeto = objetoRepository.findById(id);
        return objeto.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Objeto create(@RequestBody Objeto objeto) {
        return objetoRepository.save(objeto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Objeto> update(@PathVariable int id, @RequestBody Objeto objetoActualizado) {
        return objetoRepository.findById(id)
                .map(objeto -> {
                    objeto.setNombre(objetoActualizado.getNombre());
                    objeto.setDescripcion(objetoActualizado.getDescripcion());
                    objeto.setCategoria(objetoActualizado.getCategoria());
                    objeto.setPrecio(objetoActualizado.getPrecio());
                    return ResponseEntity.ok(objetoRepository.save(objeto));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!objetoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        objetoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
