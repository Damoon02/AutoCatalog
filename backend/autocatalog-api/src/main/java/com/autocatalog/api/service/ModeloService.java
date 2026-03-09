package com.autocatalog.api.service;

import com.autocatalog.api.model.Marca;
import com.autocatalog.api.model.Modelo;
import com.autocatalog.api.repository.MarcaRepository;
import com.autocatalog.api.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // ModeloService es una clase de servicio que maneja la lógica de negocio relacionada con los modelos de automóviles.
@RequiredArgsConstructor // Lombok genera un constructor con todos los campos finales, en este caso, los campos modeloRepository y marcaRepository.
public class ModeloService {

    // Repositorios para acceder a los datos de modelos y marcas.
    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;

    // Método para listar todos los modelos de automóviles.
    public List<Modelo> listarTodos() {
        return modeloRepository.findAll();
    }

    // Método para buscar un modelo por su ID. Si no se encuentra, lanza una excepción.
    public Modelo buscarPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado con id: " + id));
    }

    // Método para listar todos los modelos de una marca específica. Verifica que la marca exista antes de listar los modelos.
    public List<Modelo> listarPorMarca(Long marcaId) {
        if (!marcaRepository.existsById(marcaId)) {
            throw new RuntimeException("Marca no encontrada con id: " + marcaId);
        }

        return modeloRepository.findByMarcaId(marcaId);
    }

    // Método para guardar un nuevo modelo. Verifica que la marca exista y que no exista un modelo con el mismo nombre para esa marca antes de guardarlo.
    public Modelo guardar(Modelo modelo) {
        Long marcaId = modelo.getMarca().getId();

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + marcaId));

        if (modeloRepository.existsByNombreAndMarcaId(modelo.getNombre(), marcaId)) {
            throw new RuntimeException("Ya existe un modelo con ese nombre para esta marca");
        }

        modelo.setMarca(marca);
        return modeloRepository.save(modelo);
    }

    // Método para actualizar un modelo existente. Verifica que el modelo exista, que la marca exista y que el nuevo nombre no esté en uso por otro modelo de la misma marca antes de actualizarlo.
    public Modelo actualizar(Long id, Modelo modeloActualizado) {
        Modelo modeloExistente = buscarPorId(id);

        Long marcaId = modeloActualizado.getMarca().getId();

        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + marcaId));

        boolean nombreCambio = !modeloExistente.getNombre().equalsIgnoreCase(modeloActualizado.getNombre());
        boolean marcaCambio = !modeloExistente.getMarca().getId().equals(marcaId);

        if ((nombreCambio || marcaCambio)
                && modeloRepository.existsByNombreAndMarcaId(modeloActualizado.getNombre(), marcaId)) {
            throw new RuntimeException("Ya existe un modelo con ese nombre para esta marca");
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

    // Método para eliminar un modelo por su ID. Verifica que el modelo exista antes de eliminarlo.
    public void eliminar(Long id) {
        Modelo modelo = buscarPorId(id);
        modeloRepository.delete(modelo);
    }
}