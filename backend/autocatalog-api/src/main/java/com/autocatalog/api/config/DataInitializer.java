package com.autocatalog.api.config;

import com.autocatalog.api.model.*;
import com.autocatalog.api.repository.MarcaRepository;
import com.autocatalog.api.repository.ModeloRepository;
import com.autocatalog.api.repository.UsuarioRepository;
import com.autocatalog.api.repository.VersionVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component // Clase para inicializar datos de prueba en la base de datos al iniciar la aplicación
@RequiredArgsConstructor // Anotación de Lombok para generar un constructor con los campos finales (inyección de dependencias) 

// Implementa CommandLineRunner para ejecutar el método run() al iniciar la aplicación
public class DataInitializer implements CommandLineRunner {

    // Repositorios para acceder a la base de datos y el codificador de contraseñas para crear usuarios con contraseñas seguras
    private final UsuarioRepository usuarioRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final VersionVehiculoRepository versionVehiculoRepository;
    private final PasswordEncoder passwordEncoder;

    // Método que se ejecuta al iniciar la aplicación para cargar datos de prueba en la base de datos
    @Override
    public void run(String... args) throws Exception {
        inicializarUsuarios();
        inicializarCatalogo();
    }

    // Método para inicializar usuarios de prueba en la base de datos
    private void inicializarUsuarios() {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            usuarioRepository.save(admin);
        }

        if (!usuarioRepository.existsByUsername("user")) {
            Usuario user = Usuario.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .role(Role.USER)
                    .build();

            usuarioRepository.save(user);
        }
    }

    // Método para inicializar el catálogo de marcas, modelos y versiones de vehículos en la base de datos
    private void inicializarCatalogo() {
        if (marcaRepository.count() > 0) {
            return;
        }

        Marca ram = Marca.builder()
                .nombre("RAM")
                .paisOrigen("Estados Unidos")
                .build();

        Marca ford = Marca.builder()
                .nombre("Ford")
                .paisOrigen("Estados Unidos")
                .build();

        marcaRepository.save(ram);
        marcaRepository.save(ford);

        Modelo ram1500 = Modelo.builder()
                .nombre("1500")
                .anio(2024)
                .motor("5.7L V8 HEMI")
                .combustible("Gasolina")
                .transmision("Automática")
                .descripcionBreve("Pickup versátil para trabajo y uso personal")
                .imagenUrl("https://ejemplo.com/ram1500.jpg")
                .marca(ram)
                .build();

        Modelo ram2500 = Modelo.builder()
                .nombre("2500")
                .anio(2024)
                .motor("6.4L V8")
                .combustible("Gasolina")
                .transmision("Automática")
                .descripcionBreve("Pickup de mayor capacidad para trabajo pesado")
                .imagenUrl("https://ejemplo.com/ram2500.jpg")
                .marca(ram)
                .build();

        Modelo ranger = Modelo.builder()
                .nombre("Ranger")
                .anio(2024)
                .motor("2.3L EcoBoost")
                .combustible("Gasolina")
                .transmision("Automática")
                .descripcionBreve("Pickup mediana con enfoque práctico y moderno")
                .imagenUrl("https://ejemplo.com/ranger.jpg")
                .marca(ford)
                .build();

        modeloRepository.save(ram1500);
        modeloRepository.save(ram2500);
        modeloRepository.save(ranger);

        VersionVehiculo laramie = VersionVehiculo.builder()
                .nombreVersion("Laramie")
                .traccion("4x4")
                .caballosFuerza(395)
                .capacidadCarga(900.5)
                .precioReferencia(new BigDecimal("1250000.00"))
                .descripcionBreve("Versión premium con buen equilibrio entre lujo y capacidad")
                .modelo(ram1500)
                .build();

        VersionVehiculo tradesman = VersionVehiculo.builder()
                .nombreVersion("Tradesman")
                .traccion("4x2")
                .caballosFuerza(395)
                .capacidadCarga(980.0)
                .precioReferencia(new BigDecimal("980000.00"))
                .descripcionBreve("Versión enfocada en trabajo y funcionalidad")
                .modelo(ram1500)
                .build();

        VersionVehiculo limited = VersionVehiculo.builder()
                .nombreVersion("Limited")
                .traccion("4x4")
                .caballosFuerza(405)
                .capacidadCarga(870.0)
                .precioReferencia(new BigDecimal("1399000.00"))
                .descripcionBreve("Versión tope con equipamiento avanzado")
                .modelo(ram2500)
                .build();

        VersionVehiculo xlt = VersionVehiculo.builder()
                .nombreVersion("XLT")
                .traccion("4x4")
                .caballosFuerza(270)
                .capacidadCarga(750.0)
                .precioReferencia(new BigDecimal("890000.00"))
                .descripcionBreve("Versión equilibrada entre confort y utilidad")
                .modelo(ranger)
                .build();

        versionVehiculoRepository.save(laramie);
        versionVehiculoRepository.save(tradesman);
        versionVehiculoRepository.save(limited);
        versionVehiculoRepository.save(xlt);
    }
}