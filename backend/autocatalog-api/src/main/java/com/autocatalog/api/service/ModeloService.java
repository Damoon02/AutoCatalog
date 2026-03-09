package com.autocatalog.api.service;

import com.autocatalog.api.exception.DuplicateResourceException;
import com.autocatalog.api.exception.ResourceNotFoundException;
import com.autocatalog.api.model.Marca;
import com.autocatalog.api.model.Modelo;
import com.autocatalog.api.repository.MarcaRepository;
import com.autocatalog.api.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Servicio para manejar la lógica de negocio relacionada con los modelos, incluyendo operaciones CRUD y validaciones para evitar duplicados y manejar recursos no encontrados
@RequiredArgsConstructor // Anotación de Lombok para generar un constructor con los campos finales, facilitando la inyección de dependencias
public class ModeloService {

    // Repositorios para acceder a los datos de los modelos y las marcas en la base de datos, inyectados a través del constructor generado por Lombok
    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;

    // Método para listar todos los modelos disponibles en la base de datos, devolviendo una lista de objetos Modelo
    public List<Modelo> listarTodos() {
        return modeloRepository.findAll();
    }

    // Método para buscar un modelo por su ID, lanzando una excepción ResourceNotFoundException si no se encuentra el modelo con el ID proporcionado
    public Modelo buscarPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado con id: " + id));
    }

    // Método para listar todos los modelos asociados a una marca específica, verificando primero que la marca exista y luego devolviendo una lista de modelos relacionados, lanzando una excepción ResourceNotFoundException si no se encuentra la marca con el ID proporcionado
    public List<Modelo> listarPorMarca(Long marcaId) {
        if (!marcaRepository.existsById(marcaId)) {
            throw new ResourceNotFoundException("Marca no encontrada con id: " + marcaId);
        }
        return modeloRepository.findByMarcaId(marcaId);
    }

    // Método para guardar un nuevo modelo en la base de datos, verificando primero que la marca asociada exista y luego asegurándose de que no exista un modelo con el mismo nombre para esa marca, lanzando las excepciones correspondientes si se encuentra un recurso no encontrado o un duplicado
    public Modelo guardar(Modelo modelo) {
        Long marcaId = modelo.getMarca().getId();

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id: " + marcaId));

        if (modeloRepository.existsByNombreAndMarcaId(modelo.getNombre(), marcaId)) {
            throw new DuplicateResourceException("Ya existe un modelo con ese nombre para esta marca");
        }

        modelo.setMarca(marca);
        return modeloRepository.save(modelo);
    }

    // Método para actualizar un modelo existente, buscando primero el modelo por su ID y luego actualizando sus campos, verificando también que la marca asociada exista y que el nuevo nombre no cause un conflicto con otro modelo existente para esa marca, y lanzando las excepciones correspondientes si se encuentra un recurso no encontrado o un duplicado
    public Modelo actualizar(Long id, Modelo modeloActualizado) {
        Modelo modeloExistente = buscarPorId(id);
        Long marcaId = modeloActualizado.getMarca().getId();

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id: " + marcaId));

        boolean nombreCambio = !modeloExistente.getNombre().equalsIgnoreCase(modeloActualizado.getNombre());
        boolean marcaCambio = !modeloExistente.getMarca().getId().equals(marcaId);

        if ((nombreCambio || marcaCambio)
                && modeloRepository.existsByNombreAndMarcaId(modeloActualizado.getNombre(), marcaId)) {
            throw new DuplicateResourceException("Ya existe un modelo con ese nombre para esta marca");
        }

        modeloExistente.setNombre(modeloActualizado.getNombre());
        modeloExistente.setAnio(modeloActualizado.getAnio());
        modeloExistente.setMotor(modeloActualizado.getMotor());
        modeloExistente.setCombustible(modeloActualizado.getCombustible());
        modeloExistente.setTransmision(modeloActualizado.getTransmision());
        modeloExistente.setDescripcionBreve(modeloActualizado.getDescripcionBreve());
        modeloExistente.setImagenUrl(modeloActualizado.getImagenUrl());
        modeloExistente.setMarca(marca);

        return modeloRepository.save(modeloExistente);
    }

    // Método para eliminar un modelo por su ID, buscando primero el modelo para asegurarse de que existe y luego eliminándolo de la base de datos, lanzando una excepción ResourceNotFoundException si no se encuentra el modelo con el ID proporcionado
    public void eliminar(Long id) {
        Modelo modelo = buscarPorId(id);
        modeloRepository.delete(modelo);
    }
}