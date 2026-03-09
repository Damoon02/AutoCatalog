package com.autocatalog.api.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Configuration class for setting up Spring Security, including authentication and authorization rules
public class SecurityConfig {

    // Services for handling user details and JWT filtering, injected via constructor
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    // Constructor for injecting the required services into the security configuration
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    // Security filter chain configuration that defines how requests are secured, including which endpoints are public and which require authentication, as well as the use of JWT for stateless sessions
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean para el codificador de contraseñas, utilizando BCrypt para asegurar las contraseñas almacenadas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para el proveedor de autenticación, que utiliza el servicio de detalles de usuario personalizado y el codificador de contraseñas para autenticar a los usuarios
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Bean para el administrador de autenticación, que se obtiene a partir de la configuración de autenticación proporcionada por Spring Security
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}