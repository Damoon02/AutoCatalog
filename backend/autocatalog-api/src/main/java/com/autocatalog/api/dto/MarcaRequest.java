package com.autocatalog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarcaRequest {

    // DTO para la solicitud de creación o actualización de una marca, con validaciones para los campos nombre y país de origen
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no debe exceder 80 caracteres")
    private String nombre;

    // Validación para el campo país de origen, que es obligatorio y no debe exceder los 80 caracteres
    @NotBlank(message = "El país de origen es obligatorio")
    @Size(max = 80, message = "El país de origen no debe exceder 80 caracteres")
    private String paisOrigen;
}