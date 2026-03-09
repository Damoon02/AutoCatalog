package com.autocatalog.api.controller;

import com.autocatalog.api.model.Modelo;
import com.autocatalog.api.service.ModeloService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/modelos") // Define la ruta base para todas las operaciones relacionadas con "Modelo"
@RequiredArgsConstructor // Genera un constructor con todos los campos finales (en este caso, modeloService)
public class ModeloController {

    // Inyecta el servicio de Modelo para manejar la lógica de negocio
    private final ModeloService modeloService;

    // Método para listar todos los modelos
    @GetMapping
    public ResponseEntity<List<Modelo>> listarTodos() {
        return ResponseEntity.ok(modeloService.listarTodos());
    }

    // Método para buscar un modelo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

    // Método para listar modelos por marca
    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Modelo>> listarPorMarca(@PathVariable Long marcaId) {
        return ResponseEntity.ok(modeloService.listarPorMarca(marcaId));
    }

    // Método para guardar un nuevo modelo
    @PostMapping
    public ResponseEntity<Modelo> guardar(@RequestBody Modelo modelo) {
        Modelo nuevoModelo = modeloService.guardar(modelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoModelo);
    }

    // Método para actualizar un modelo existente
    @PutMapping("/{id}")
    public ResponseEntity<Modelo> actualizar(@PathVariable Long id, @RequestBody Modelo modelo) {
        return ResponseEntity.ok(modeloService.actualizar(id, modelo));
    }

    // Método para eliminar un modelo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        modeloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}