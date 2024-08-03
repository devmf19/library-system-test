# 📚 Biblioteca Pública API

Bienvenido al proyecto de la API para la gestión de una biblioteca pública. Este sistema permite la administración de libros, miembros, préstamos y usuarios en una biblioteca sin costo por los libros.

## 🚀 Tecnologías y Herramientas

- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.3.2
- **Base de Datos**: H2 (en memoria)
- **Librerías**:
    - Spring Security 6.0.1
    - MapStruct 1.5.2.Final
    - Springdoc OpenAPI 2.1.0
    - JJWT 0.9.1
    - Lombok 1.18.24

## 🔐 Autenticación y Autorización

El sistema utiliza autenticación y autorización basada en roles:

- **USER**: Acceso a operaciones básicas como búsqueda y creación de libros.
- **ADMIN**: Acceso completo a todas las operaciones, incluyendo la gestión de usuarios.
- **DISABLED**: No tiene acceso a ninguna operación ni al sistema.

### Gestión de Usuarios

La gestión de usuarios está reservada exclusivamente para los administradores (ADMIN).

## 📚 Entidades de la Biblioteca

- **USER**: Usuarios que utilizan el sistema.
- **MEMBER**: Miembros que pueden realizar préstamos de libros.
- **SECTION**: Secciones donde se guardan los libros.
- **BOOK**: Libros disponibles para préstamo (con total y stock).
- **AUTHOR**: Autores de los libros.
- **INVOICE**: Préstamos realizados por los miembros.
- **INVOICEITEM**: Relación entre libros y préstamos (muchos a muchos).
- **INVOICESTATUS**: Estados de un préstamo:
    - **BORROWED**: libros entregados al miembro
    - **RETURNED**: libros devueltos
- **ROLE**: Roles de usuarios (USER, ADMIN, DISABLED).

## 🗂 Estructura del Proyecto

- **`config`**: Clases de configuración (OpenAPI, Swagger).
- **`controller`**: Controladores para manejar las peticiones.
- **`dto`**: Clases DTO para requests y responses.
- **`enums`**: Enumeraciones para roles y estados de préstamos.
- **`exception`**: Clases para manejo de excepciones personalizadas.
- **`mapper`**: Mapeadores para convertir DTOs a entidades y viceversa (MapStruct).
- **`model`**: Clases de representación del modelo de base de datos.
- **`repository`**: Interfaces JPA para acceso a datos.
- **`security`**: Configuración de seguridad, autenticación y autorización.
- **`service`**: Servicios de la aplicación.
- **`util`**: Clases de utilidad, constantes, etc.

## 🛠️ Configuración y Ejecución con Docker

Para levantar el sistema, utiliza el Dockerfile y docker-compose proporcionados.

1. **Construir la imagen Docker**:
   ```bash
   docker build -t library-api .

2. **Levantar el contenedor:**:
   ```bash
   docker-compose up 

## 🌐 Acceder a la Swagger UI

1. Navega a: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
2. Al final de la página, encontrarás los endpoints de autenticación y registro.
3. Iniciar sesión:
    - **Usuario**: admin
    - **Contraseña**: admin
4. Copia el token obtenido y asígnalo en "Authorize" al inicio de la página.

## 🛠️ Consola de H2

1. Accede a: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - **Usuario**: admin
    - **Contraseña**: admin


## 📜 Documentación

Para más detalles sobre la API y sus endpoints, consulta la Swagger UI y la documentación de OpenAPI.
