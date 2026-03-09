package com.autocatalog.api.service;

import com.autocatalog.api.exception.DuplicateResourceException;
import com.autocatalog.api.exception.ResourceNotFoundException;
import com.autocatalog.api.model.Marca;
import com.autocatalog.api.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Servicio para manejar la lógica de negocio relacionada con las marcas, incluyendo operaciones CRUD y validaciones para evitar duplicados y manejar recursos no encontrados
@RequiredArgsConstructor // Anotación de Lombok para generar un constructor con los campos finales, facilitando la inyección de dependencias
public class MarcaService {

    // Repositorio para acceder a los datos de las marcas en la base de datos, inyectado a través del constructor generado por Lombok
    private final MarcaRepository marcaRepository;

    // Método para listar todas las marcas disponibles en la base de datos, devolviendo una lista de objetos Marca
    public List<Marca> listarTodas() {
        return marcaRepository.findAll();
    }

    // Método para buscar una marca por su ID, lanzando una excepción ResourceNotFoundException si no se encuentra la marca con el ID proporcionado
    public Marca buscarPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id: " + id));
    }

    // Método para guardar una nueva marca en la base de datos, verificando primero si ya existe una marca con el mismo nombre para evitar duplicados, y lanzando una excepción DuplicateResourceException si se encuentra un conflicto
    public Marca guardar(Marca marca) {
        if (marcaRepository.existsByNombre(marca.getNombre())) {
            throw new DuplicateResourceException("Ya existe una marca con ese nombre");
        }
        return marcaRepository.save(marca);
    }

    // Método para actualizar una marca existente, buscando primero la marca por su ID y luego actualizando sus campos, verificando también que el nuevo nombre no cause un conflicto con otra marca existente, y lanzando las excepciones correspondientes si se encuentra un recurso no encontrado o un duplicado
    public Marca actualizar(Long id, Marca marcaActualizada) {
        Marca marcaExistente = buscarPorId(id);

        if (!marcaExistente.getNombre().equalsIgnoreCase(marcaActualizada.getNombre())
                && marcaRepository.existsByNombre(marcaActualizada.getNombre())) {
            throw new DuplicateResourceException("Ya existe una marca con ese nombre");
        }

        marcaExistente.setNombre(marcaActualizada.getNombre());
        marcaExistente.setPaisOrigen(marcaActualizada.getPaisOrigen());

        return marcaRepository.save(marcaExistente);
    }

    // Método para eliminar una marca por su ID, buscando primero la marca para asegurarse de que existe y luego eliminándola de la base de datos, lanzando una excepción ResourceNotFoundException si no se encuentra la marca con el ID proporcionado
    public void eliminar(Long id) {
        Marca marca = buscarPorId(id);
        marcaRepository.delete(marca);
    }
}