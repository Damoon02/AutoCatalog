package com.autocatalog.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity // Indica que esta clase es una entidad JPA, lo que significa que se mapeará a una tabla en la base de datos.
@Table(name = "marcas") // Especifica el nombre de la tabla en la base de datos a la que se mapeará esta entidad. En este caso, la tabla se llamará "marcas".
@Getter // Genera automáticamente los métodos getter para todos los campos de la clase.
@Setter // Genera automáticamente los métodos setter para todos los campos de la clase.
@NoArgsConstructor // Genera un constructor sin argumentos para la clase, lo que es necesario para que JPA pueda crear instancias de la entidad.
@AllArgsConstructor // Genera un constructor con argumentos para todos los campos de la clase, lo que facilita la creación de objetos Marca con todos sus atributos inicializados.
@Builder // Habilita el patrón de diseño Builder para esta clase, lo que permite construir objetos Marca de manera más legible y flexible, especialmente cuando hay muchos campos.

public class Marca {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String paisOrigen;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, orphanRemoval = true) // Define la relación uno a muchos con la entidad Modelo, indicando que el campo "marca" en la clase Modelo es el propietario de la relación. Además, se especifica que las operaciones de persistencia (como guardar o eliminar) se propaguen a los modelos relacionados, y que si una marca se elimina, también se eliminen sus modelos asociados.
    @JsonManagedReference // Esta anotación se utiliza para manejar la serialización JSON de las relaciones bidireccionales. Indica que esta parte de la relación es la "gestora" y se incluirá en la salida JSON, mientras que la parte opuesta (en Modelo) se marcará con @JsonBackReference para evitar referencias circulares.
    @Builder.Default // Esta anotación de Lombok se utiliza para proporcionar un valor predeterminado para el campo "modelos" cuando se utiliza el patrón Builder. En este caso, se inicializa con una nueva instancia de ArrayList, lo que garantiza que la lista no sea nula incluso si no se establece explícitamente al construir un objeto Marca.
    private List<Modelo> modelos = new ArrayList<>(); // Define una lista de objetos Modelo que están asociados con esta marca. La anotación @OneToMany indica que una marca puede tener muchos modelos, y la lista se inicializa como una nueva ArrayList para evitar problemas de nullabilidad.

}