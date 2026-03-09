package com.autocatalog.api.security;

import com.autocatalog.api.model.Usuario;
import com.autocatalog.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Service para cargar los detalles del usuario desde la base de datos, utilizado por Spring Security para autenticar a los usuarios
@RequiredArgsConstructor // Anotacion de Lombok para generar un constructor para los campos finales, en este caso el repositorio de usuarios
public class CustomUserDetailsService implements UserDetailsService {

    // Repositorio para acceder a los datos de los usuarios, inyectado a través del constructor generado por Lombok
    private final UsuarioRepository usuarioRepository;

    // Método para cargar los detalles del usuario por su nombre de usuario, que busca el usuario en la base de datos y devuelve un objeto UserDetails con la información del usuario y sus roles
    @Override
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