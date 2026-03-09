package com.autocatalog.api.controller;

import com.autocatalog.api.dto.MarcaRequest;
import com.autocatalog.api.model.Marca;
import com.autocatalog.api.service.MarcaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Controlador REST para manejar las solicitudes relacionadas con las marcas de vehículos, proporcionando endpoints para operaciones CRUD y utilizando un servicio para la lógica de negocio
@RequestMapping("/api/marcas") // Ruta base para todas las operaciones relacionadas con las marcas, lo que permite organizar los endpoints de manera coherente y fácil de entender
@RequiredArgsConstructor // Anotación de Lombok para generar un constructor con los campos finales, facilitando la inyección de dependencias del servicio MarcaService
public class MarcaController {

    // Servicio para manejar la lógica de negocio relacionada con las marcas, inyectado a través del constructor generado por Lombok, lo que permite separar la lógica de negocio de la capa de presentación y facilitar el mantenimiento del código
    private final MarcaService marcaService;

    // Endpoint para listar todas las marcas disponibles en la base de datos, devolviendo una respuesta HTTP con el estado OK y una lista de objetos Marca en el cuerpo de la respuesta
    @GetMapping
    public ResponseEntity<List<Marca>> listarTodas() {
        return ResponseEntity.ok(marcaService.listarTodas());
    }

    // Endpoint para buscar una marca por su ID, devolviendo una respuesta HTTP con el estado OK y el objeto Marca correspondiente en el cuerpo de la respuesta, o lanzando una excepción si no se encuentra la marca con el ID proporcionado
    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    // Endpoint para crear una nueva marca, recibiendo los datos de la marca en el cuerpo de la solicitud como un objeto MarcaRequest, validando los datos de entrada y devolviendo una respuesta HTTP con el estado CREATED y el objeto Marca creado en el cuerpo de la respuesta, o lanzando una excepción si se encuentra un conflicto con una marca existente
    @PostMapping
    public ResponseEntity<Marca> guardar(@Valid @RequestBody MarcaRequest request) {
        Marca marca = Marca.builder()
                .nombre(request.getNombre())
                .paisOrigen(request.getPaisOrigen())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.guardar(marca));
    }

    // Endpoint para actualizar una marca existente, recibiendo el ID de la marca a actualizar en la ruta y los nuevos datos de la marca en el cuerpo de la solicitud como un objeto MarcaRequest, validando los datos de entrada y devolviendo una respuesta HTTP con el estado OK y el objeto Marca actualizado en el cuerpo de la respuesta, o lanzando una excepción si no se encuentra la marca con el ID proporcionado o si se encuentra un conflicto con otra marca existente
    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizar(@PathVariable Long id, @Valid @RequestBody MarcaRequest request) {
        Marca marca = Marca.builder()
                .nombre(request.getNombre())
                .paisOrigen(request.getPaisOrigen())
                .build();

        return ResponseEntity.ok(marcaService.actualizar(id, marca));
    }

    // Endpoint para eliminar una marca por su ID, recibiendo el ID de la marca a eliminar en la ruta, devolviendo una respuesta HTTP con el estado NO_CONTENT si la eliminación es exitosa, o lanzando una excepción si no se encuentra la marca con el ID proporcionado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        marcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}