Minimarket API - Backend ServiceEste repositorio contiene la API RESTful para la gestión de inventario del sistema Minimarket.
Desarrollado con Java 25 y Spring Boot 4.02, enfocado en la integridad de datos, manejo robusto de errores y arquitectura en capas.

Características TécnicasEl sistema implementa lógica de negocio avanzada para garantizar la consistencia de la base de datos:
Arquitectura en Capas: Separación estricta de responsabilidades (Controller, Service, Repository, Entity).
Validaciones "Fail-Fast": Verificación inmediata de nulidad y formato antes de procesar lógica de negocio.
Integridad Referencial Lógica: Protección contra eliminación en cascada (impide borrar Categorías con Productos asociados).
Manejo de Concurrencia en Edición: Algoritmo de verificación de duplicados que permite la actualización de registros sin conflictos de unicidad (existsByNombreAndIdNot).
Gestión de Excepciones Personalizada: Mapeo semántico de errores HTTP:
404 Not Found: Recurso no existente.
400 Bad Request: Violación de reglas de negocio o formato.

Stack Tecnológico
Lenguaje: Java 25 (LTS)
Framework: Spring Boot 4.02
Spring Web: Para la exposición de endpoints REST.
Spring Data JPA: Para la persistencia y abstracción de SQL.
Jakarta Validation: Para restricciones de entidades (@NotBlank, @Min, etc.).
Base de Datos: MySQL / H2 (Configurable).
Herramientas: Maven, Lombok, Postman.

Estructura del ProyectoBashcom.an.minimarket
├── controller       # Endpoints REST (API Layer)
├── service          # Lógica de Negocio (Interfaces e Implementaciones)
├── repository       # Acceso a Datos (JPA Extensions)
├── model            # Entidades de Persistencia (JPA Entities)
└── exceptions       # Custom Exceptions (Global Error Handling)

# Navegar al directorio
cd minimarket-backend

# Limpiar y empaquetar
mvn clean package

# Ejecutar
mvn spring-boot:run
La API estará disponible en: http://localhost:8080👨‍💻 Autor Jeremy Angeles - Backend Developer
