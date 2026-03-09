package com.autocatalog.api.repository;

import com.autocatalog.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositoy extends JpaRepository<Usuario, Long> { // Extiende JpaRepository para proporcionar métodos CRUD básicos

    Optional<Usuario> findByUsername(String username); // Método para encontrar un usuario por su nombre de usuario

    boolean existsByUsername(String username); // Método para verificar si un usuario con un nombre de usuario específico ya existe
}