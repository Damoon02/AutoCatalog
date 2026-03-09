package com.autocatalog.api.service;

import com.autocatalog.api.model.Marca;
import com.autocatalog.api.repository.MarcaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // MarcaService es una clase de servicio que maneja la lógica de negocio relacionada con las marcas de automóviles.
@RequiredArgsConstructor // Lombok genera un constructor con todos los campos finales, en este caso, el campo marcaRepository.
public class MarcaService {

    private final MarcaRepository marcaRepository;

    // Método para listar todas las marcas de automóviles.
    public List<Marca> listarTodas() {
        return marcaRepository.findAll();
    }

    // Método para buscar una marca por su ID. Si no se encuentra, lanza una excepción.
    public Marca buscarPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + id));
    }

    // Método para guardar una nueva marca. Verifica que no exista una marca con el mismo nombre antes de guardarla.
    public Marca guardar(Marca marca) {
        if (marcaRepository.existsByNombre(marca.getNombre())) {
            throw new RuntimeException("Ya existe una marca con ese nombre");
        }

        return marcaRepository.save(marca);
    }

    // Método para actualizar una marca existente. Verifica que la marca exista y que el nuevo nombre no esté en uso por otra marca.
    public Marca actualizar(Long id, Marca marcaActualizada) {
        Marca marcaExistente = buscarPorId(id);

        if (!marcaExistente.getNombre().equalsIgnoreCase(marcaActualizada.getNombre())
                && marcaRepository.existsByNombre(marcaActualizada.getNombre())) {
            throw new RuntimeException("Ya existe una marca con ese nombre");
        }

        marcaExistente.setNombre(marcaActualizada.getNombre());
        marcaExistente.setPaisOrigen(marcaActualizada.getPaisOrigen());

        return marcaRepository.save(marcaExistente);
    }

    // Método para eliminar una marca por su ID. Verifica que la marca exista antes de eliminarla.
    public void eliminar(Long id) {
        Marca marca = buscarPorId(id);
        marcaRepository.delete(marca);
    }
}