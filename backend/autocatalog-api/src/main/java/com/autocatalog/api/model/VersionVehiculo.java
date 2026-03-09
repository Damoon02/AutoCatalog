package com.autocatalog.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "versiones_vehiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VersionVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombreVersion;

    @Column(nullable = false, length = 50)
    private String traccion;

    @Column(nullable = false)
    private Integer caballosFuerza;

    @Column(nullable = false)
    private Double capacidadCarga;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioReferencia;

    @Column(nullable = false, length = 300)
    private String descripcionBreve;

    @ManyToOne(fetch = FetchType.LAZY) // Especifica que la relación entre VersionVehiculo y Modelo es de muchos a uno, lo que significa que varias versiones de vehículo pueden estar asociadas a un mismo modelo. El atributo fetch = FetchType.LAZY indica que la información del modelo asociado no se cargará automáticamente cuando se cargue una versión de vehículo, sino que se cargará solo cuando se acceda explícitamente a esa información.
    @JoinColumn(name = "modelo_id", nullable = false) // Especifica que la columna "modelo_id" en la tabla "versiones_vehiculo" se utilizará para establecer la relación con la tabla "modelos". Además, se indica que esta columna no puede ser nula, lo que significa que cada versión de vehículo debe estar asociada a un modelo.
    @JsonBackReference // Esta anotación se utiliza para manejar la serialización JSON de las relaciones bidireccionales. Indica que esta parte de la relación es la "inversa" y no se incluirá en la salida JSON, evitando así referencias circulares que podrían causar problemas al convertir los objetos a JSON.
    private Modelo modelo;
}