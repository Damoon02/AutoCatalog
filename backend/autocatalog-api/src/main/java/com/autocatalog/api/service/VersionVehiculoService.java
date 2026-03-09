package com.autocatalog.api.service;

import com.autocatalog.api.model.Modelo;
import com.autocatalog.api.model.VersionVehiculo;
import com.autocatalog.api.repository.ModeloRepository;
import com.autocatalog.api.repository.VersionVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // VersionVehiculoService es una clase de servicio que maneja la lógica de negocio relacionada con las versiones de vehículos.
@RequiredArgsConstructor // Lombok genera un constructor con todos los campos finales, en este caso, los campos versionVehiculoRepository y modeloRepository.
public class VersionVehiculoService {

    // Repositorios para acceder a los datos de versiones de vehículos y modelos.
    private final VersionVehiculoRepository versionVehiculoRepository;
    private final ModeloRepository modeloRepository;

    // Método para listar todas las versiones de vehículos.
    public List<VersionVehiculo> listarTodas() {
        return versionVehiculoRepository.findAll();
    }

    // Método para buscar una versión de vehículo por su ID. Si no se encuentra, lanza una excepción.
    public VersionVehiculo buscarPorId(Long id) {
        return versionVehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Versión no encontrada con id: " + id));
    }

    // Método para listar todas las versiones de un modelo específico. Verifica que el modelo exista antes de listar las versiones.
    public List<VersionVehiculo> listarPorModelo(Long modeloId) {
        if (!modeloRepository.existsById(modeloId)) {
            throw new RuntimeException("Modelo no encontrado con id: " + modeloId);
        }

        return versionVehiculoRepository.findByModeloId(modeloId);
    }

    // Método para guardar una nueva versión de vehículo. Verifica que el modelo exista y que no exista una versión con el mismo nombre para ese modelo antes de guardarla.
    public VersionVehiculo guardar(VersionVehiculo versionVehiculo) {
        Long modeloId = versionVehiculo.getModelo().getId();

        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado con id: " + modeloId));

        if (versionVehiculoRepository.existsByNombreVersionAndModeloId(
                versionVehiculo.getNombreVersion(), modeloId)) {
            throw new RuntimeException("Ya existe una versión con ese nombre para este modelo");
        }

        versionVehiculo.setModelo(modelo);
        return versionVehiculoRepository.save(versionVehiculo);
    }

    // Método para actualizar una versión de vehículo existente. Verifica que la versión exista, que el modelo exista y que el nuevo nombre no esté en uso por otra versión del mismo modelo antes de actualizarla.
    public VersionVehiculo actualizar(Long id, VersionVehiculo versionActualizada) {
        VersionVehiculo versionExistente = buscarPorId(id);

        Long modeloId = versionActualizada.getModelo().getId();

        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado con id: " + modeloId));

        boolean nombreCambio = !versionExistente.getNombreVersion()
                .equalsIgnoreCase(versionActualizada.getNombreVersion());

        boolean modeloCambio = !versionExistente.getModelo().getId().equals(modeloId);

        if ((nombreCambio || modeloCambio)
                && versionVehiculoRepository.existsByNombreVersionAndModeloId(
                versionActualizada.getNombreVersion(), modeloId)) {
            throw new RuntimeException("Ya existe una versión con ese nombre para este modelo");
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

    // Método para eliminar una versión de vehículo por su ID. Verifica que la versión exista antes de eliminarla.
    public void eliminar(Long id) {
        VersionVehiculo versionVehiculo = buscarPorId(id);
        versionVehiculoRepository.delete(versionVehiculo);
    }
}