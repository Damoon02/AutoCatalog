package com.autocatalog.api.security;

import com.autocatalog.api.model.Usuario;
import com.autocatalog.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Implementación personalizada de UserDetailsService para cargar usuarios desde la base de datos
@RequiredArgsConstructor // Genera un constructor con los campos finales (usuarioRepository)
public class CustomUserDetailsService implements UserDetailsService {

    // Repositorio para acceder a los datos de los usuarios
    private final UsuarioRepository usuarioRepository;

    @Override // Método para cargar un usuario por su nombre de usuario
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()))
        );
    }
}