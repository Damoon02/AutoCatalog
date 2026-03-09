package com.autocatalog.api.controller;

import com.autocatalog.api.dto.ModeloRequest;
import com.autocatalog.api.model.Marca;
import com.autocatalog.api.model.Modelo;
import com.autocatalog.api.service.ModeloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Controlador REST para manejar las solicitudes relacionadas con los modelos de vehículos, proporcionando endpoints para operaciones CRUD y utilizando un servicio para la lógica de negocio
@RequestMapping("/api/modelos") // Ruta base para todas las operaciones relacionadas con los modelos, lo que permite organizar los endpoints de manera coherente y fácil de entender
@RequiredArgsConstructor // Anotación de Lombok para generar un constructor con los campos finales, facilitando la inyección de dependencias del servicio ModeloService
public class ModeloController {

    // Servicio para manejar la lógica de negocio relacionada con los modelos, inyectado a través del constructor generado por Lombok, lo que permite separar la lógica de negocio de la capa de presentación y facilitar el mantenimiento del código
    private final ModeloService modeloService;

    // Endpoint para listar todos los modelos disponibles en la base de datos, devolviendo una respuesta HTTP con el estado OK y una lista de objetos Modelo en el cuerpo de la respuesta
    @GetMapping
    public ResponseEntity<List<Modelo>> listarTodos() {
        return ResponseEntity.ok(modeloService.listarTodos());
    }

    // Endpoint para buscar un modelo por su ID, devolviendo una respuesta HTTP con el estado OK y el objeto Modelo correspondiente en el cuerpo de la respuesta, o lanzando una excepción si no se encuentra el modelo con el ID proporcionado
    @GetMapping("/{id}")
    public ResponseEntity<Modelo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

// Endpoint para listar todos los modelos asociados a una marca específica, recibiendo el ID de la marca en la ruta, devolviendo una respuesta HTTP con el estado OK y una lista de objetos Modelo relacionados en el cuerpo de la respuesta, o lanzando una excepción si no se encuentra la marca con el ID proporcionado
    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<Modelo>> listarPorMarca(@PathVariable Long marcaId) {
        return ResponseEntity.ok(modeloService.listarPorMarca(marcaId));
    }

    // Endpoint para crear un nuevo modelo, recibiendo los datos del modelo en el cuerpo de la solicitud como un objeto ModeloRequest, validando los datos de entrada y devolviendo una respuesta HTTP con el estado CREATED y el objeto Modelo creado en el cuerpo de la respuesta, o lanzando una excepción si se encuentra un conflicto con un modelo existente o si no se encuentra la marca asociada
    @PostMapping
    public ResponseEntity<Modelo> guardar(@Valid @RequestBody ModeloRequest request) {
        Modelo modelo = Modelo.builder()
                .nombre(request.getNombre())
                .anio(request.getAnio())
                .motor(request.getMotor())
                .combustible(request.getCombustible())
                .transmision(request.getTransmision())
                .descripcionBreve(request.getDescripcionBreve())
                .imagenUrl(request.getImagenUrl())
                .marca(Marca.builder().id(request.getMarcaId()).build())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.guardar(modelo));
    }

    // Endpoint para actualizar un modelo existente, recibiendo el ID de la marca a actualizar en la ruta y los nuevos datos del modelo en el cuerpo de la solicitud como un objeto ModeloRequest, validando los datos de entrada y devolviendo una respuesta HTTP con el estado OK y el objeto Modelo actualizado en el cuerpo de la respuesta, o lanzando una excepción si no se encuentra el modelo con el ID proporcionado, si se encuentra un conflicto con otro modelo existente o si no se encuentra la marca asociada
    @PutMapping("/{id}")
    public ResponseEntity<Modelo> actualizar(@PathVariable Long id, @Valid @RequestBody ModeloRequest request) {
        Modelo modelo = Modelo.builder()
                .nombre(request.getNombre())
                .anio(request.getAnio())
                .motor(request.getMotor())
                .combustible(request.getCombustible())
                .transmision(request.getTransmision())
                .descripcionBreve(request.getDescripcionBreve())
                .imagenUrl(request.getImagenUrl())
                .marca(Marca.builder().id(request.getMarcaId()).build())
                .build();

        return ResponseEntity.ok(modeloService.actualizar(id, modelo));
    }

    // Endpoint para eliminar un modelo por su ID, recibiendo el ID del modelo a eliminar en la ruta, devolviendo una respuesta HTTP con el estado NO_CONTENT si la eliminación es exitosa, o lanzando una excepción si no se encuentra el modelo con el ID proporcionado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        modeloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}