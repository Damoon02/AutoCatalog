package com.autocatalog.api.controller;

import com.autocatalog.api.model.Marca;
import com.autocatalog.api.service.MarcaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/marcas") // Define la ruta base para todas las operaciones relacionadas con "Marca"
@RequiredArgsConstructor // Genera un constructor con todos los campos finales (en este caso, marcaService)
public class MarcaController {

    // Inyecta el servicio de Marca para manejar la lógica de negocio
    private final MarcaService marcaService;

    // Método para listar todas las marcas
    @GetMapping
    public ResponseEntity<List<Marca>> listarTodas() {
        return ResponseEntity.ok(marcaService.listarTodas());
    }

    // Método para buscar una marca por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    // Método para guardar una nueva marca
    @PostMapping
    public ResponseEntity<Marca> guardar(@RequestBody Marca marca) {
        Marca nuevaMarca = marcaService.guardar(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    // Método para actualizar una marca existente
    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizar(@PathVariable Long id, @RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.actualizar(id, marca));
    }

    // Método para eliminar una marca por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        marcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}