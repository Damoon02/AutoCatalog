package com.autocatalog.api.repository;

import com.autocatalog.api.model.VersionVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VersionVehiculoRepository extends JpaRepository<VersionVehiculo, Long> {

    List<VersionVehiculo> findByModeloId(long modeloId);

    Optional<VersionVehiculo> findByNombreVersion(String nombreVersion);

    boolean existsByNombreVersionAndModeloId(String nombreVersion, Long modeloId);
}