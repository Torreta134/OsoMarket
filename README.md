# OsornoMarket

Aplicacion tipo Marketplace para Osorno, hecha con kotlin, android studio, proyecto Educativo
Autor: Benjamin Torres
🐻 OsoMarket — Backend
API REST desarrollada con Ktor + Kotlin conectada a Neon (PostgreSQL), siguiendo Arquitectura Limpia.

🏗️ Arquitectura
El proyecto sigue un flujo unidireccional donde cada capa solo habla con la siguiente:

Routes → Services → Repositories → Database

Routes reciben las peticiones HTTP y responden al cliente.
Services contienen la lógica de negocio: validaciones, hashing, generación de tokens.
Repositories ejecutan las consultas SQL con Exposed. Cada uno implementa una interfaz (Contract).
Mappers convierten datos entre capas usando funciones de extensión de Kotlin.
Domain Models son clases puras que representan las entidades del negocio.
⚠️ Las rutas nunca acceden directamente a los repositorios. Siempre pasan por los Services.

📁 Estructura del Proyecto
Application.kt — Punto de entrada y configuración de Ktor
data/database/ — Tablas de Exposed: UsersTable, ProgramsTable
data/dto/ — Objetos de transferencia: requests y responses
data/mapper/ — Funciones de extensión para convertir entre capas
domain/model/ — Modelos de dominio: User, Program
domain/repository/ — Interfaces: UserRepositoryContract, ProgramRepositoryContract
repository/ — Implementaciones: UserRepository, ProgramRepository
service/ — Lógica de negocio: AuthService, ProgramService, TokenService
security/ — PasswordHasher con BCrypt
routes/ — Endpoints: AuthRoutes, ProgramRoutes
🗄️ Base de Datos
PostgreSQL alojado en Neon (cloud)
Conexiones manejadas con HikariCP
Las tablas se crean automáticamente al iniciar con SchemaUtils.create()
Tabla users: id, name, email (unique), password

Tabla programs: id, name, description, price

🔐 Seguridad
Contraseñas: se hashean con BCrypt antes de guardarlas. Al hacer login se verifica el hash.
Autenticación: se genera un JWT al registrarse o loguearse. Las rutas de escritura (POST, PUT, DELETE) están protegidas y requieren el token.
🌐 Endpoints
Autenticación
POST /auth/register — Registrar usuario, devuelve token
POST /auth/login — Iniciar sesión, devuelve token
Programas
GET /programs — Listar todos (pública)
GET /programs/{id} — Obtener uno (pública)
POST /programs — Crear programa (🔒 requiere JWT)
PUT /programs/{id} — Actualizar programa (🔒 requiere JWT)
DELETE /programs/{id} — Eliminar programa (🔒 requiere JWT)
⚠️ Manejo de Errores
ConflictException (409) — Email ya registrado
UnauthorizedException (401) — Credenciales inválidas
NotFoundException (404) — Programa no encontrado
🛠️ Tecnologías
Kotlin · Ktor · Exposed · PostgreSQL (Neon) · HikariCP · BCrypt · JWT · Kotlinx Serialization

🚀 Cómo Ejecutar
Configurar credenciales de Neon en application.conf
Ejecutar Application.kt
El servidor inicia en http://localhost:8080
Las tablas se crean solas al arrancar
