package com.autocatalog.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity // Le dice a JPA que esta clase es una entidad que se mapeará a una tabla en la base de datos
@Table(name = "usuarios") // Especifica el nombre de la tabla en la base de datos que se mapeará a esta entidad
@Getter // Genera automáticamente los métodos getter para todos los campos de la clase
@Setter // Genera automáticamente los métodos setter para todos los campos de la clase
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Builder // Permite usar el patrón de diseño Builder para crear instancias de esta clase de manera más legible

public class Usuario {

    @Id // Indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Especifica que el valor de este campo se generará automáticamente por la base de datos (auto-incremental)
    private Long id; 

    @Column(nullable = false, unique = true, length = 50) // Especifica que este campo no puede ser nulo, debe ser único y tiene una longitud máxima de 50 caracteres
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Especifica que el valor de este campo se almacenará como una cadena en la base de datos, en lugar de su ordinal (número)
    @Column(nullable = false, length = 20)
    private Role role;
}