package com.autocatalog.api.controller;

import com.autocatalog.api.dto.VersionVehiculoRequest;
import com.autocatalog.api.model.Modelo;
import com.autocatalog.api.model.VersionVehiculo;
import com.autocatalog.api.service.VersionVehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Controlador REST para manejar las solicitudes relacionadas con las versiones de vehículos, proporcionando endpoints para operaciones CRUD y utilizando un servicio para la lógica de negocio
@RequestMapping("/api/versiones") // Ruta base para todas las operaciones relacionadas con las versiones de vehículos, lo que permite organizar los endpoints de manera coherente y fácil de entender
@RequiredArgsConstructor // Anotación de Lombok para generar un constructor con los campos finales, facilitando la inyección de dependencias del servicio VersionVehiculoService
public class VersionVehiculoController {

    // Servicio para manejar la lógica de negocio relacionada con las versiones de vehículos, inyectado a través del constructor generado por Lombok, lo que permite separar la lógica de negocio de la capa de presentación y facilitar el mantenimiento del código
    private final VersionVehiculoService versionVehiculoService;

    // Endpoint para listar todas las versiones de vehículos disponibles en la base de datos, devolviendo una respuesta HTTP con el estado OK y una lista de objetos VersionVehiculo en el cuerpo de la respuesta
    @GetMapping
    public ResponseEntity<List<VersionVehiculo>> listarTodas() {
        return ResponseEntity.ok(versionVehiculoService.listarTodas());
    }

    // Endpoint para buscar una versión de vehículo por su ID, devolviendo una respuesta HTTP con el estado OK y el objeto VersionVehiculo correspondiente en el cuerpo de la respuesta, o lanzando una excepción si no se encuentra la versión con el ID proporcionado
    @GetMapping("/{id}")
    public ResponseEntity<VersionVehiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(versionVehiculoService.buscarPorId(id));
    }

    // Endpoint para listar todas las versiones de vehículos asociadas a un modelo específico, verificando primero que el modelo exista y luego devolviendo una lista de versiones relacionadas, lanzando una excepción si no se encuentra el modelo con el ID proporcionado
    @GetMapping("/modelo/{modeloId}")
    public ResponseEntity<List<VersionVehiculo>> listarPorModelo(@PathVariable Long modeloId) {
        return ResponseEntity.ok(versionVehiculoService.listarPorModelo(modeloId));
    }

    // Endpoint para guardar una nueva versión de vehículo en la base de datos, verificando primero que el modelo asociado exista y luego asegurándose de que no exista una versión con el mismo nombre para ese modelo, lanzando las excepciones correspondientes si se encuentra un recurso no encontrado o un duplicado
    @PostMapping
    public ResponseEntity<VersionVehiculo> guardar(@Valid @RequestBody VersionVehiculoRequest request) {
        VersionVehiculo version = VersionVehiculo.builder()
                .nombreVersion(request.getNombreVersion())
                .traccion(request.getTraccion())
                .caballosFuerza(request.getCaballosFuerza())
                .capacidadCarga(request.getCapacidadCarga())
                .precioReferencia(request.getPrecioReferencia())
                .descripcionBreve(request.getDescripcionBreve())
                .modelo(Modelo.builder().id(request.getModeloId()).build())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(versionVehiculoService.guardar(version));
    }

    // Endpoint para actualizar una versión de vehículo existente, buscando primero la versión por su ID y luego actualizando sus campos, verificando también que el modelo asociado exista y que el nuevo nombre no cause un conflicto con otra versión existente para ese modelo, y lanzando las excepciones correspondientes si se encuentra un recurso no encontrado o un duplicado
    @PutMapping("/{id}")
    public ResponseEntity<VersionVehiculo> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody VersionVehiculoRequest request) {
        VersionVehiculo version = VersionVehiculo.builder()
                .nombreVersion(request.getNombreVersion())
                .traccion(request.getTraccion())
                .caballosFuerza(request.getCaballosFuerza())
                .capacidadCarga(request.getCapacidadCarga())
                .precioReferencia(request.getPrecioReferencia())
                .descripcionBreve(request.getDescripcionBreve())
                .modelo(Modelo.builder().id(request.getModeloId()).build())
                .build();

        return ResponseEntity.ok(versionVehiculoService.actualizar(id, version));
    }

    // End point para eliminar una versión de vehículo por su ID, recibiendo el ID de la versión a eliminar en la ruta, devolviendo una respuesta HTTP con el estado NO_CONTENT si la eliminación es exitosa, o lanzando una excepción si no se encuentra la versión con el ID proporcionado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        versionVehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}