package com.autocatalog.api.service;

import com.autocatalog.api.exception.DuplicateResourceException;
import com.autocatalog.api.exception.ResourceNotFoundException;
import com.autocatalog.api.model.Modelo;
import com.autocatalog.api.model.VersionVehiculo;
import com.autocatalog.api.repository.ModeloRepository;
import com.autocatalog.api.repository.VersionVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Servicio para manejar la lógica de negocio relacionada con las versiones de vehículos, incluyendo operaciones CRUD y validaciones para evitar duplicados y manejar recursos no encontrados
@RequiredArgsConstructor // Anotación de Lombok para generar un constructor con los campos finales, facilitando la inyección de dependencias
public class VersionVehiculoService {

    // Repositorios para acceder a los datos de las versiones de vehículos y los modelos en la base de datos, inyectados a través del constructor generado por Lombok
    private final VersionVehiculoRepository versionVehiculoRepository;
    private final ModeloRepository modeloRepository;

    // Método para listar todas las versiones de vehículos disponibles en la base de datos, devolviendo una lista de objetos VersionVehiculo
    public List<VersionVehiculo> listarTodas() {
        return versionVehiculoRepository.findAll();
    }

    // Método para buscar una versión de vehículo por su ID, lanzando una excepción ResourceNotFoundException si no se encuentra la versión con el ID proporcionado
    public VersionVehiculo buscarPorId(Long id) {
        return versionVehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Versión no encontrada con id: " + id));
    }

    // Método para listar todas las versiones de vehículos asociadas a un modelo específico, verificando primero que el modelo exista y luego devolviendo una lista de versiones relacionadas, lanzando una excepción ResourceNotFoundException si no se encuentra el modelo con el ID proporcionado
    public List<VersionVehiculo> listarPorModelo(Long modeloId) {
        if (!modeloRepository.existsById(modeloId)) {
            throw new ResourceNotFoundException("Modelo no encontrado con id: " + modeloId);
        }
        return versionVehiculoRepository.findByModeloId(modeloId);
    }

    // Método para guardar una nueva versión de vehículo en la base de datos, verificando primero que el modelo asociado exista y luego asegurándose de que no exista una versión con el mismo nombre para ese modelo, lanzando las excepciones correspondientes si se encuentra un recurso no encontrado o un duplicado
    public VersionVehiculo guardar(VersionVehiculo versionVehiculo) {
        Long modeloId = versionVehiculo.getModelo().getId();

        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado con id: " + modeloId));

        if (versionVehiculoRepository.existsByNombreVersionAndModeloId(
                versionVehiculo.getNombreVersion(), modeloId)) {
            throw new DuplicateResourceException("Ya existe una versión con ese nombre para este modelo");
        }

        versionVehiculo.setModelo(modelo);
        return versionVehiculoRepository.save(versionVehiculo);
    }

    // Método para actualizar una versión de vehículo existente, buscando primero la versión por su ID y luego actualizando sus campos, verificando también que el modelo asociado exista y que el nuevo nombre no cause un conflicto con otra versión existente para ese modelo, y lanzando las excepciones correspondientes si se encuentra un recurso no encontrado o un duplicado
    public VersionVehiculo actualizar(Long id, VersionVehiculo versionActualizada) {
        VersionVehiculo versionExistente = buscarPorId(id);
        Long modeloId = versionActualizada.getModelo().getId();

        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado con id: " + modeloId));

        boolean nombreCambio = !versionExistente.getNombreVersion()
                .equalsIgnoreCase(versionActualizada.getNombreVersion());
        boolean modeloCambio = !versionExistente.getModelo().getId().equals(modeloId);

        if ((nombreCambio || modeloCambio)
                && versionVehiculoRepository.existsByNombreVersionAndModeloId(
                versionActualizada.getNombreVersion(), modeloId)) {
            throw new DuplicateResourceException("Ya existe una versión con ese nombre para este modelo");
        }

        versionExistente.setNombreVersion(versionActualizada.getNombreVersion());
        versionExistente.setTraccion(versionActualizada.getTraccion());
        versionExistente.setCaballosFuerza(versionActualizada.getCaballosFuerza());
        versionExistente.setCapacidadCarga(versionActualizada.getCapacidadCarga());
        versionExistente.setPrecioReferencia(versionActualizada.getPrecioReferencia());
        versionExistente.setDescripcionBreve(versionActualizada.getDescripcionBreve());
        versionExistente.setModelo(modelo);

        return versionVehiculoRepository.save(versionExistente);
    }

    // Método para eliminar una versión de vehículo por su ID, buscando primero la versión para asegurarse de que existe y luego eliminándola de la base de datos, lanzando una excepción ResourceNotFoundException si no se encuentra la versión con el ID proporcionado
    public void eliminar(Long id) {
        VersionVehiculo versionVehiculo = buscarPorId(id);
        versionVehiculoRepository.delete(versionVehiculo);
    }
}