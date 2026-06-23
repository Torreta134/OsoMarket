# OsornoMarket

Aplicacion tipo Marketplace para Osorno, hecha con kotlin, android studio, proyecto Educativo
Autor: Benjamin Torres
🐻 OsoMarket — Backend
API REST desarrollada con Ktor + Kotlin conectada a Neon (PostgreSQL), siguiendo Arquitectura Limpia.

OsoMarket — Backend
Backend desarrollado con Ktor + Kotlin, conectado a PostgreSQL (Neon), con Arquitectura Limpia.

Arquitectura
Routes → Services → Repositories (Contracts) → Database
Las rutas nunca acceden directamente a los repositorios, siempre pasan por los Services.

Seguridad
Las contraseñas se hashean con BCrypt antes de guardarse. Al hacer login se verifica el hash y se genera un JWT válido por 1 hora. Las rutas de escritura están protegidas con autenticación JWT.

Endpoints
POST /auth/register — Registro de usuario, devuelve token POST /auth/login — Inicio de sesión, devuelve token GET /programs — Listar programas GET /programs/{id} — Obtener programa POST /programs — Crear programa 🔒 PUT /programs/{id} — Actualizar programa 🔒 DELETE /programs/{id} — Eliminar programa 🔒

Excepciones
ConflictException (409) — Email ya registrado UnauthorizedException (401) — Credenciales inválidas NotFoundException (404) — Programa no encontrado

Tecnologías
Kotlin · Ktor · Exposed · PostgreSQL (Neon) · HikariCP · BCrypt · JWT · Kotlinx Serialization

Ejecución
Configurar credenciales en application.conf, ejecutar Application.kt. El servidor inicia en http://localhost:8080 y las tablas se crean automáticamente.
