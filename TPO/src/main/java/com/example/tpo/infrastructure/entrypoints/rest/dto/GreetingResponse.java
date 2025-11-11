package com.example.tpo.infrastructure.entrypoints.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO expuesto por la API para representar un saludo.
 */
@Schema(description = "Respuesta con el mensaje de saludo")
public record GreetingResponse(
        @Schema(description = "Mensaje generado por el servicio", example = "Hola, Mundo!")
        String message
) {
}
