package com.eslegacyapp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eslegacyapp.api.model.Enemigo;
import com.eslegacyapp.api.model.Objeto;
import com.eslegacyapp.api.repository.EnemigoRepository;
import com.eslegacyapp.api.repository.ObjetoRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enemigos")
public class EnemigoController {

    @Autowired
    private EnemigoRepository enemigoRepository;
    private ObjetoRepository objetoRepository;
    
    public EnemigoController(EnemigoRepository enemigoRepository, ObjetoRepository objetoRepository) {
        this.enemigoRepository = enemigoRepository;
        this.objetoRepository = objetoRepository;
    }

    @GetMapping
    public List<Enemigo> getAll() {
        return enemigoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enemigo> getById(@PathVariable int id) {
        Optional<Enemigo> enemigo = enemigoRepository.findById(id);
        return enemigo.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Enemigo create(@RequestBody Enemigo enemigo) {
        return enemigoRepository.save(enemigo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enemigo> update(@PathVariable int id, @RequestBody Enemigo enemigoActualizado) {
        return enemigoRepository.findById(id)
                .map(enemigo -> {
                    enemigo.setNombre(enemigoActualizado.getNombre());
                    enemigo.setDescripcion(enemigoActualizado.getDescripcion());
                    enemigo.setEjemplosHabilidades(enemigoActualizado.getEjemplosHabilidades());
                    return ResponseEntity.ok(enemigoRepository.save(enemigo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!enemigoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        enemigoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{idEnemigo}/objetos/{idObjeto}")
    public ResponseEntity<Enemigo> asignarObjeto(
            @PathVariable int idEnemigo,
            @PathVariable int idObjeto) {

        Enemigo enemigo = enemigoRepository.findById(idEnemigo)
                .orElseThrow();

        Objeto objeto = objetoRepository.findById(idObjeto)
                .orElseThrow();

        enemigo.getObjetos().add(objeto);

        return ResponseEntity.ok(enemigoRepository.save(enemigo));
    }

}
