package com.autocatalog.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeloRequest {

    // DTO para la solicitud de creación o actualización de un modelo, con validaciones para los campos nombre, año, motor, combustible, transmisión, descripción breve, URL de imagen y marcaId
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no debe exceder 80 caracteres")
    private String nombre;

    // Validaciones para el campo año, que es obligatorio y debe ser un número entre 1900 y 2100
    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser válido")
    @Max(value = 2100, message = "El año debe ser válido")
    private Integer anio;

    // Validaciones para el campo motor, que es obligatorio y no debe exceder los 80 caracteres
    @NotBlank(message = "El motor es obligatorio")
    @Size(max = 80, message = "El motor no debe exceder 80 caracteres")
    private String motor;

    // Validaciones para el campo combustible, que es obligatorio y no debe exceder los 50 caracteres
    @NotBlank(message = "El combustible es obligatorio")
    @Size(max = 50, message = "El combustible no debe exceder 50 caracteres")
    private String combustible;

    // Validaciones para el campo transmisión, que es obligatorio y no debe exceder los 50 caracteres
    @NotBlank(message = "La transmisión es obligatoria")
    @Size(max = 50, message = "La transmisión no debe exceder 50 caracteres")
    private String transmision;

    // Validaciones para el campo descripción breve, que es obligatorio y no debe exceder los 300 caracteres
    @NotBlank(message = "La descripción breve es obligatoria")
    @Size(max = 300, message = "La descripción no debe exceder 300 caracteres")
    private String descripcionBreve;

    // Validaciones para el campo URL de imagen, que no es obligatorio pero si se proporciona no debe exceder los 500 caracteres
    @Size(max = 500, message = "La URL de imagen no debe exceder 500 caracteres")
    private String imagenUrl;

    // Validación para el campo marcaId, que es obligatorio y debe ser un número positivo
    @NotNull(message = "La marca es obligatoria")
    private Long marcaId;
}