# OsornoMarket

Aplicacion tipo Marketplace para Osorno, hecha con kotlin, android studio, proyecto Educativo
Autor: Benjamin Torres
🐻 OsoMarket — Backend
Aplicacion tipo Marketplace para Osorno, hecha con kotlin, android studio, proyecto Educativo
Autor: Benjamin Torres
API REST desarrollada con Ktor + Kotlin conectada a Neon (PostgreSQL), siguiendo Arquitectura Limpia.

🏗️ Arquitectura
El proyecto sigue un flujo unidireccional donde cada capa solo se comunica con la siguiente:

Routes → Services → Repositories → Database
Routes: Reciben las peticiones HTTP y responden al cliente.
Services: Contienen la lógica de negocio (validaciones, hashing, tokens).
Repositories: Ejecutan las consultas SQL a través de Exposed. Implementan interfaces (Contracts).
Database: Tablas definidas con Exposed ORM.
Mappers: Convierten datos entre capas usando funciones de extensión de Kotlin.
Domain Models: Clases puras que representan las entidades del negocio.
Las rutas nunca acceden directamente a los repositorios. Siempre pasan por los Services.

📁 Estructura


backend/src/main/kotlin/.../backend/
├── Application.kt                 → Punto de entrada y configuración
├── data/
│   ├── database/                  → Tablas (UsersTable, ProgramsTable)
│   ├── dto/                       → Objetos de transferencia (Request/Response)
│   └── mapper/                    → Conversores entre capas
├── domain/
│   ├── model/                     → Modelos de dominio (User, Program)
│   └── repository/                → Interfaces/Contratos
├── repository/                    → Implementación de los contratos
├── service/                       → Lógica de negocio + Excepciones
├── security/                      → PasswordHasher (BCrypt)
└── routes/                        → Endpoints HTTP
🗄️ Base de Datos
PostgreSQL alojado en Neon (cloud)
Conexiones manejadas con HikariCP
Las tablas se crean automáticamente al iniciar con SchemaUtils.create()
Tabla	Columnas
users	id, name, email (unique), password
programs	id, name, description, price
🔐 Seguridad
Contraseñas: Se hashean con BCrypt antes de guardarlas. Al hacer login se verifica el hash.
Autenticación: Se genera un JWT al registrarse o loguearse. Las rutas de escritura (POST, PUT, DELETE de programs) están protegidas y requieren el token.
🌐 Endpoints
Autenticación
Método	Ruta	Descripción
POST	/auth/register	Registrar usuario → devuelve token
POST	/auth/login	Iniciar sesión → devuelve token
Programas
Método	Ruta	Auth	Descripción
GET	/programs	No	Listar todos
GET	/programs/{id}	No	Obtener uno
POST	/programs	🔒 Sí	Crear programa
PUT	/programs/{id}	🔒 Sí	Actualizar programa
DELETE	/programs/{id}	🔒 Sí	Eliminar programa
⚠️ Manejo de Errores
Excepción	Código	Cuándo se lanza
ConflictException	409	Email ya registrado
UnauthorizedException	401	Credenciales inválidas
NotFoundException	404	Programa no encontrado
🛠️ Tecnologías
Kotlin · Ktor · Exposed · PostgreSQL (Neon) · HikariCP · BCrypt · JWT · Kotlinx Serialization

🚀 Cómo Ejecutar
Configurar credenciales de Neon en application.conf
Ejecutar Application.kt
El servidor inicia en http://localhost:8080
Las tablas se crean solas en Neon al arrancar
