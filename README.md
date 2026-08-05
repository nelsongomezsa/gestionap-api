# GestionAp API

API REST para la gestión de habitaciones en alquiler, inquilinos y contratos. Es la evolución backend del proyecto de escritorio **GestionAp** (JavaFX + MySQL), reescrita como aplicación en capas con Spring Boot para practicar diseño de endpoints REST, Spring Data JPA y arquitectura de servicios.

## Stack

- Java 17
- Spring Boot 3.3.4 (Web, Data JPA, Validation)
- H2 (base de datos en memoria, perfil por defecto)
- MySQL (perfil alternativo, mismo motor que la versión de escritorio)
- Maven

## Arquitectura

```
model/       → entidades JPA (Habitacion, Inquilino, Contrato)
repository/  → interfaces Spring Data JPA
service/     → lógica de negocio y transacciones
controller/  → endpoints REST
dto/         → objetos de entrada/salida que no son entidades
exception/   → excepciones propias y manejador global de errores
```

Las tres entidades:

- **Habitacion**: `codigo`, `direccion`, `precioMensual`, `estado` (`DISPONIBLE`, `OCUPADA`, `EN_MANTENIMIENTO`).
- **Inquilino**: `nombre`, `documentoIdentidad`, `email`, `telefono`.
- **Contrato**: vincula una `Habitacion` con un `Inquilino` entre `fechaInicio` y `fechaFin` (fecha fin nula = contrato activo).

Al crear un contrato, la habitación pasa automáticamente a `OCUPADA`; al finalizarlo, vuelve a `DISPONIBLE`. Esa regla vive en `ContratoService`, no en el controller.

## Cómo ejecutar

### Opción 1: H2 en memoria (por defecto, sin configuración)

```bash
mvn spring-boot:run
```

La API arranca en `http://localhost:8080`. Se cargan datos de ejemplo automáticamente (`data.sql`). Consola H2 disponible en `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:gestionapdb`, usuario `sa`, sin contraseña).

### Opción 2: MySQL

1. Crear la base de datos (o dejar que `createDatabaseIfNotExist=true` la cree).
2. Ajustar usuario/contraseña en `src/main/resources/application-mysql.properties`.
3. Ejecutar:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Endpoints

### Habitaciones

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/habitaciones` | Listar todas (opcional `?estado=DISPONIBLE`) |
| GET | `/api/habitaciones/{id}` | Buscar por id |
| POST | `/api/habitaciones` | Crear |
| PUT | `/api/habitaciones/{id}` | Actualizar |
| DELETE | `/api/habitaciones/{id}` | Eliminar |

```bash
curl -X POST http://localhost:8080/api/habitaciones \
  -H "Content-Type: application/json" \
  -d '{"codigo":"H-301","direccion":"Calle Sol 3, 3A","precioMensual":500}'
```

### Inquilinos

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/inquilinos` | Listar todos |
| GET | `/api/inquilinos/{id}` | Buscar por id |
| POST | `/api/inquilinos` | Crear |
| PUT | `/api/inquilinos/{id}` | Actualizar |
| DELETE | `/api/inquilinos/{id}` | Eliminar |

```bash
curl -X POST http://localhost:8080/api/inquilinos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana Torres","documentoIdentidad":"33333333C","email":"ana.torres@example.com","telefono":"600555666"}'
```

### Contratos

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/contratos` | Listar todos (opcional `?activos=true`) |
| GET | `/api/contratos/{id}` | Buscar por id |
| GET | `/api/contratos/habitacion/{habitacionId}` | Contratos de una habitación |
| GET | `/api/contratos/inquilino/{inquilinoId}` | Contratos de un inquilino |
| POST | `/api/contratos` | Crear (requiere habitación `DISPONIBLE`) |
| PATCH | `/api/contratos/{id}/finalizar` | Finalizar contrato (libera la habitación) |
| DELETE | `/api/contratos/{id}` | Eliminar |

```bash
curl -X POST http://localhost:8080/api/contratos \
  -H "Content-Type: application/json" \
  -d '{"habitacionId":1,"inquilinoId":1,"fechaInicio":"2026-08-01"}'

curl -X PATCH http://localhost:8080/api/contratos/1/finalizar \
  -H "Content-Type: application/json" \
  -d '{"fechaFin":"2026-12-31"}'
```

## Manejo de errores

Todas las respuestas de error siguen el mismo formato JSON (timestamp, status, error, message, path y, en validaciones, el detalle campo a campo):

```json
{
  "timestamp": "2026-08-05T10:15:30",
  "status": 404,
  "error": "Not Found",
  "message": "Habitacion no encontrada con id: 99",
  "path": "/api/habitaciones/99"
}
```

- `404` → recurso no encontrado (`ResourceNotFoundException`)
- `409` → conflicto de negocio, p. ej. código/documento/email duplicado, o habitación no disponible (`IllegalArgumentException`)
- `400` → datos de entrada inválidos (validación de Bean Validation)

## Notas

- Este proyecto es una ampliación personal del GestionAp de escritorio, no un encargo de empresa: sirve para practicar y demostrar diseño de API REST en capas.
- Con `spring.jpa.hibernate.ddl-auto=update`, el esquema se genera automáticamente a partir de las entidades; en un entorno real se sustituiría por migraciones versionadas (Flyway/Liquibase).
