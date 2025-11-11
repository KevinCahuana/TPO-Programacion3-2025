# TPO Programación 3 - API Hexagonal

Este proyecto inicializa una API REST basada en Spring Boot 3 que sigue los principios de la arquitectura hexagonal. Se incluye un endpoint de ejemplo documentado con OpenAPI/Swagger y se configura `log4j2` para emitir logs estructurados que facilitan la trazabilidad.

## Requisitos

- Java 17
- Maven 3.9+

## Estructura de directorios

La siguiente estructura muestra los paquetes principales y cómo se relacionan con las capas de la arquitectura hexagonal:

```
src/main/java/com/example/tpo
├── TpoApplication.java                     # Punto de entrada de Spring Boot
├── application
│   └── service
│       └── GreetingService.java            # Implementación del caso de uso (servicio de aplicación)
├── domain
│   ├── model
│   │   └── Greeting.java                   # Entidad/record de dominio
│   └── port
│       └── in
│           └── GreetingQuery.java          # Puerto de entrada (contrato del caso de uso)
└── infrastructure
    ├── configuration
    │   └── OpenApiConfig.java              # Configuración de OpenAPI/Swagger
    └── entrypoints
        └── rest
            ├── GreetingController.java     # Adaptador primario (API REST)
            └── dto
                └── GreetingResponse.java   # DTO expuesto por la API
```

Los adaptadores secundarios (por ejemplo, persistencia) pueden agregarse dentro de `infrastructure` respetando este esquema.

## Dependencias destacadas

- `springdoc-openapi-starter-webmvc-ui` para documentar automáticamente la API y exponer Swagger UI.
- `spring-boot-starter-log4j2` reemplaza el logger por defecto de Spring Boot y habilita logs en formato JSON mediante `log4j2`.

## Ejecución del proyecto

```bash
cd TPO
./mvnw spring-boot:run
```

Al iniciar, la aplicación queda disponible en `http://localhost:8080`.

### Documentación Swagger

La documentación interactiva se genera en `http://localhost:8080/swagger-ui/index.html`. También se puede obtener el documento OpenAPI en formato JSON desde `http://localhost:8080/v3/api-docs`.

## Endpoint de ejemplo

| Método | Ruta                | Descripción                               |
|--------|---------------------|-------------------------------------------|
| GET    | `/api/v1/greetings` | Devuelve un saludo personalizado opcional |

Parámetros de consulta:

- `name` (opcional): nombre de la persona a saludar.

Ejemplo de solicitud:

```bash
curl "http://localhost:8080/api/v1/greetings?name=Ana"
```

Respuesta esperada:

```json
{
  "message": "Hola, Ana!"
}
```

## Logging estructurado

Los logs se configuran mediante `log4j2-spring.xml` para emitirse en formato JSON a la consola. Cada interacción registra eventos clave (`event`, `status`, `requestId`, `durationMs`, etc.) para facilitar el seguimiento extremo a extremo.

Ejemplo de log generado al invocar el endpoint:

```json
{"timeMillis":1729123456789,"thread":"http-nio-8080-exec-1","level":"INFO","loggerName":"com.example.tpo.infrastructure.entrypoints.rest.GreetingController","message":"event=greeting_request status=END requestId=5c4... durationMs=12","service":"tpo-api"}
```

## Pruebas

Para validar que el contexto de Spring Boot levanta correctamente:

```bash
cd TPO
./mvnw test
```

---

Este es un punto de partida para continuar evolucionando el dominio y los adaptadores respetando los principios de arquitectura hexagonal.
