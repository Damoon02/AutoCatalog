package com.autocatalog.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice // Clase para manejar excepciones globalmente en la aplicación, proporcionando respuestas estructuradas para errores comunes como recursos no encontrados, duplicados y validaciones fallidas
public class GlobalExceptionHandler {

    // Manejador para la excepción ResourceNotFoundException, que devuelve una respuesta con el estado HTTP 404 y un mensaje de error personalizado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Manejador para la excepción DuplicateResourceException, que devuelve una respuesta con el estado HTTP 409 y un mensaje de error personalizado
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // Manejador para la excepción MethodArgumentNotValidException, que se lanza cuando la validación de los datos de entrada falla, devolviendo una respuesta con el estado HTTP 400 y un mapa de errores detallado para cada campo que no pasó la validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validación fallida");

        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        body.put("fields", errores);
        return ResponseEntity.badRequest().body(body);
    }

    // Manejador para cualquier otra excepción no manejada específicamente, que devuelve una respuesta con el estado HTTP 500 y un mensaje de error genérico indicando un error interno del servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    // Método auxiliar para construir la respuesta de error, que incluye un timestamp, el código de estado HTTP y un mensaje de error personalizado
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", message);
        return ResponseEntity.status(status).body(body);
    }
}