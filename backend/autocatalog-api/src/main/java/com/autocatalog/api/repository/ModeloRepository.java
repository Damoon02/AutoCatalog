package com.autocatalog.api.repository;

import com.autocatalog.api.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findByMarcaId(Long marcaId); // Método para encontrar modelos por el ID de la marca

    Optional<Modelo> findByNombre(String nombre);

    boolean existsByNombreAndMarcaId(String nombre, Long marcaId); // Método para verificar si un modelo con un nombre específico ya existe dentro de una marca
}