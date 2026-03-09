package com.autocatalog.api.controller;

import com.autocatalog.api.model.VersionVehiculo;
import com.autocatalog.api.service.VersionVehiculoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/versiones") // Define la ruta base para todas las operaciones relacionadas con "VersionVehiculo"
@RequiredArgsConstructor // Genera un constructor con todos los campos finales (en este caso, versionVehiculoService)
public class VersionVehiculoController {

    // Inyecta el servicio de VersionVehiculo para manejar la lógica de negocio
    private final VersionVehiculoService versionVehiculoService;

    // Método para listar todas las versiones de vehículos
    @GetMapping
    public ResponseEntity<List<VersionVehiculo>> listarTodas() {
        return ResponseEntity.ok(versionVehiculoService.listarTodas());
    }

    // Método para buscar una versión de vehículo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<VersionVehiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(versionVehiculoService.buscarPorId(id));
    }

    // Método para listar versiones de vehículos por modelo
    @GetMapping("/modelo/{modeloId}")
    public ResponseEntity<List<VersionVehiculo>> listarPorModelo(@PathVariable Long modeloId) {
        return ResponseEntity.ok(versionVehiculoService.listarPorModelo(modeloId));
    }

    // Método para guardar una nueva versión de vehículo
    @PostMapping
    public ResponseEntity<VersionVehiculo> guardar(@RequestBody VersionVehiculo versionVehiculo) {
        VersionVehiculo nuevaVersion = versionVehiculoService.guardar(versionVehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVersion);
    }

    // Método para actualizar una versión de vehículo existente
    @PutMapping("/{id}")
    public ResponseEntity<VersionVehiculo> actualizar(@PathVariable Long id, @RequestBody VersionVehiculo versionVehiculo) {
        return ResponseEntity.ok(versionVehiculoService.actualizar(id, versionVehiculo));
    }

    // Método para eliminar una versión de vehículo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        versionVehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}