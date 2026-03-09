package com.autocatalog.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VersionVehiculoRequest {

    // DTO para la solicitud de creación o actualización de una versión de vehículo, con validaciones para los campos nombre de la versión, tracción, caballos de fuerza, capacidad de carga, precio de referencia, descripción breve y modeloId
    @NotBlank(message = "El nombre de la versión es obligatorio")
    @Size(max = 80, message = "El nombre de la versión no debe exceder 80 caracteres")
    private String nombreVersion;

    // Validaciones para el campo tracción, que es obligatorio y no debe exceder los 50 caracteres
    @NotBlank(message = "La tracción es obligatoria")
    @Size(max = 50, message = "La tracción no debe exceder 50 caracteres")
    private String traccion;

    // Validaciones para el campo caballos de fuerza, que es obligatorio y debe ser un número positivo
    @NotNull(message = "Los caballos de fuerza son obligatorios")
    @Positive(message = "Los caballos de fuerza deben ser mayores a 0")
    private Integer caballosFuerza;

    // Validaciones para el campo capacidad de carga, que es obligatorio y debe ser un número positivo
    @NotNull(message = "La capacidad de carga es obligatoria")
    @Positive(message = "La capacidad de carga debe ser mayor a 0")
    private Double capacidadCarga;

    // Validaciones para el campo precio de referencia, que es obligatorio y debe ser un número decimal positivo
    @NotNull(message = "El precio de referencia es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precioReferencia;

    // Validaciones para el campo descripción breve, que es obligatorio y no debe exceder los 300 caracteres
    @NotBlank(message = "La descripción breve es obligatoria")
    @Size(max = 300, message = "La descripción no debe exceder 300 caracteres")
    private String descripcionBreve;

    // Validación para el campo modeloId, que es obligatorio y debe ser un número positivo
    @NotNull(message = "El modelo es obligatorio")
    private Long modeloId;
}