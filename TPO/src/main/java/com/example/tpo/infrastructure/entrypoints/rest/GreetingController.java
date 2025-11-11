package com.example.tpo.infrastructure.entrypoints.rest;

import com.example.tpo.domain.model.Greeting;
import com.example.tpo.domain.port.in.GreetingQuery;
import com.example.tpo.infrastructure.entrypoints.rest.dto.GreetingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Primary adapter that exposes the greeting use case through an HTTP API.
 */
@RestController
@RequestMapping("/api/v1/greetings")
@Tag(name = "Greetings", description = "Operaciones para obtener saludos de ejemplo")
public class GreetingController {

    private static final Logger LOGGER = LogManager.getLogger(GreetingController.class);
    private final GreetingQuery greetingQuery;

    public GreetingController(GreetingQuery greetingQuery) {
        this.greetingQuery = greetingQuery;
    }

    @GetMapping
    @Operation(
            summary = "Obtiene un saludo personalizado",
            description = "Devuelve un mensaje de saludo. Si no se envía un nombre se utilizará 'Mundo'."
    )
    public ResponseEntity<GreetingResponse> getGreeting(
            @RequestParam(name = "name", required = false)
            @Parameter(description = "Nombre de la persona a saludar") String name) {

        String requestId = UUID.randomUUID().toString();
        Instant start = Instant.now();

        try (CloseableThreadContext.Instance ignored = CloseableThreadContext.put("requestId", requestId)) {
            LOGGER.info("event=greeting_request status=START requestId={} name={}", requestId, name);

            Greeting greeting = greetingQuery.getGreeting(name);

            Duration duration = Duration.between(start, Instant.now());
            LOGGER.info(
                    "event=greeting_request status=END requestId={} durationMs={}",
                    requestId,
                    duration.toMillis()
            );

            return ResponseEntity.ok(new GreetingResponse(greeting.message()));
        }
    }
}
