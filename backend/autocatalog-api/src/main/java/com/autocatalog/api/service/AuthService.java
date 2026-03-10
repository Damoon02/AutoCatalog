package com.autocatalog.api.service;

import com.autocatalog.api.dto.AuthRequest;
import com.autocatalog.api.dto.AuthResponse;
import com.autocatalog.api.dto.RegisterRequest;
import com.autocatalog.api.exception.DuplicateResourceException;
import com.autocatalog.api.model.Role;
import com.autocatalog.api.model.Usuario;
import com.autocatalog.api.repository.UsuarioRepository;
import com.autocatalog.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

        // Service para manejar la autenticación y registro de usuarios, utilizando JWT para la generación de tokens y Spring Security para la autenticación
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Método para manejar el proceso de login, autenticando al usuario y generando un token JWT si las credenciales son correctas
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Cargar los detalles del usuario autenticado para generar el token JWT y construir la respuesta de autenticación
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        // Extraer el rol del usuario para incluirlo en la respuesta, asumiendo que cada usuario tiene un solo rol
        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .orElse("USER");

        return AuthResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role(role)
                .build();
    }

    // Método para manejar el proceso de registro de nuevos usuarios, validando que el username no exista, codificando la contraseña y asignando el rol antes de guardar el nuevo usuario en la base de datos y generar un token JWT para el nuevo usuario registrado
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Ya existe un usuario con ese username");
        }

        // Validar y convertir el rol proporcionado en el request a un enum Role, lanzando una excepción si el rol es inválido
        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Rol inválido. Usa ADMIN o USER");
        }

        // Crear un nuevo usuario con el username, contraseña codificada y rol asignado, luego guardar el usuario en la base de datos y generar un token JWT para el nuevo usuario registrado
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        usuarioRepository.save(usuario);

        // Cargar los detalles del nuevo usuario registrado para generar el token JWT y construir la respuesta de autenticación
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getUsername());
        String token = jwtService.generateToken(userDetails);

        // Extraer el rol del nuevo usuario registrado para incluirlo en la respuesta, asumiendo que cada usuario tiene un solo rol
        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .role(usuario.getRole().name())
                .build();
    }
}