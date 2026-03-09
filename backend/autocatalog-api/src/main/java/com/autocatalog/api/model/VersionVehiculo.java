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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_id", nullable = false)
    @JsonBackReference
    private Modelo modelo;
}