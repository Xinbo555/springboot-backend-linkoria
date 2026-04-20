# Linkoria — Backend

> Trabajo de Fin de Grado · Desarrollo de Aplicaciones Multiplataforma  
> Autor: **Xinbo Liu**

---

## ¿Qué es Linkoria?

Linkoria es una aplicación de chat en tiempo real inspirada en Discord. Permite a los usuarios comunicarse mediante mensajes directos y canales dentro de servidores, gestionar amistades, reaccionar a mensajes y mucho más.

Este repositorio contiene el **backend** desarrollado con Spring Boot, siguiendo una arquitectura limpia basada en Domain-Driven Design (DDD).

---

## Tecnologías principales

- Java 23
- Spring Boot 4.x
- Spring Data JPA + Hibernate
- MySQL 8
- WebSocket (STOMP)
- JWT (autenticación)
- Springdoc OpenAPI (Swagger)
- Docker & Docker Compose
- Flyway (migraciones de base de datos)

## Configuración de la base de datos

La aplicación utiliza **MySQL**. Crea una base de datos llamada `chatapp` o deja que Spring la cree automáticamente con los siguientes credenciales mínimos en tu `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatapp?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
---

## Requisitos

- Java 23
- Maven
- Docker Desktop (para opción Docker)
- MySQL 8.0 (para opción local)

---

## Variables de entorno necesarias

| Variable | Descripción | Ejemplo |
|---|---|---|
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de MySQL | `tu_password` |
| `JWT_SECRET` | Clave secreta JWT (mínimo 32 caracteres) | `l1nk0r1a-s3cr3t-k3y-p4r4-JWT-d3s4rr0ll0-2026` |

---

## Opción A — Con Docker (recomendado)

No necesitas tener Java ni MySQL instalados.

**Requisitos:** Docker Desktop corriendo.
```bash
# Clona el repositorio
git clone https://github.com/xinboliu/linkoria-backend.git
cd linkoria-backend

# Arranca la app y la base de datos
docker-compose up --build
```

La aplicación arrancará en `http://localhost:8081`.
La base de datos se crea y migra automáticamente con Flyway.

Para parar:
```bash
docker-compose down
```

---

## Opción B — Sin Docker (local)

**Requisitos:** Java 23, Maven y MySQL 8.0 instalados.

1. Clona el repositorio:
```bash
git clone https://github.com/xinboliu/linkoria-backend.git
cd linkoria-backend
```

2. Configura las variables de entorno en IntelliJ:
```
Run → Edit Configurations → Environment Variables
SPRING_DATASOURCE_PASSWORD=tu_password_mysql
JWT_SECRET=l1nk0r1a-s3cr3t-k3y-p4r4-JWT-d3s4rr0ll0-2026
```

3. La base de datos `linkoria` se crea automáticamente al arrancar.

4. Ejecuta desde IntelliJ con el botón de play o:
```bash
mvn spring-boot:run
```

La aplicación arrancará en `http://localhost:8080`.

---

## Documentación de la API

Swagger UI disponible en:
- Local: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Docker: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

---

## Módulos implementados

### `auth` — Autenticación
Gestiona el ciclo de vida de la autenticación con doble token:
- **accessToken** JWT de 15 minutos — stateless, validado por firma criptográfica
- **refreshToken** UUID de 30 días — persistido en BD, con rotación en cada uso
- El hash de contraseñas es responsabilidad exclusiva de este módulo — el módulo `user` nunca toca passwords

### `user` — Gestión de usuarios
Gestiona los perfiles de usuario:
- Búsqueda por username parcial
- Actualización de perfil (username, email, avatarUrl)
- Value objects para `Email` y `Username` con validación en dominio

### `friendship` — Sistema de amistades
Sistema BÁSICO de relaciones entre usuarios con máquina de estados:

```
PENDING → ACCEPTED → REMOVED → PENDING
PENDING → DECLINED → PENDING
```

### `server` — Gestión de servidores

Gestiona servidores y su membresía con sistema de roles:

- **Creación** — el creador se convierte automáticamente en `OWNER` y se genera un `inviteCode` único de 12 caracteres
- **Acceso** — cualquier usuario puede unirse mediante `inviteCode`, no hay directorio público
- **Roles** — máquina de estados de roles:
```
MEMBER → ADMIN    (promovido por OWNER)
ADMIN  → MEMBER   (degradado por OWNER)
MEMBER/ADMIN → OWNER  (transferencia — el OWNER actual pasa a ADMIN)
```

- **Restricciones** — el `OWNER` no puede abandonar el servidor sin transferir la propiedad previamente
- **Eliminación** — solo el `OWNER` puede borrar el servidor, lo que elimina en cascada todos los miembros
- **Unicidad del inviteCode** — validada a nivel de caso de uso y reforzada con constraint `UNIQUE` en BD

### `channel` — Canales de texto

Gestiona los canales de texto dentro de un servidor y su agrupación por categorías:

- **Creación** — solo `OWNER` y `ADMIN` pueden crear canales y categorías dentro de un servidor
- **Eliminación** — solo `OWNER` y `ADMIN` pueden eliminar canales y categorías
- **Lectura** — cualquier miembro del servidor puede leer y listar canales
- **Categorías** — los canales pueden agruparse bajo una `ChannelCategory` dentro del mismo servidor. La categoría es opcional, un canal puede existir sin categoría
- **Restricciones** — al eliminar un servidor se eliminan en cascada todos sus canales y categorías. Al eliminar una categoría, sus canales pasan a `categoryId = null` sin eliminarse

## Estado del proyecto

Este proyecto está en desarrollo activo como parte del TFG. Módulos implementados hasta el momento:

- [x] `user` — Gestión de usuarios
- [x] `auth` — Autenticación JWT
- [x] `friendship` — Sistema de amistades
- [x] `server` — Servidores, miembros y roles
- [x] `channel` — Canales de texto dentro de servidores
- [x] `conversation` — Mensajes directos entre usuarios
- [ ] `message` — Mensajería en tiempo real (WebSocket)
- [ ] `notification` — Notificaciones push
- [ ] `presence` — Estado de conexión en tiempo real
- [ ] `attachment` — Subida y gestión de archivos
- [ ] `invitation` — Invitaciones a servidores
- [ ] `receipt` — Confirmaciones de lectura
- [ ] `typing` — Indicador de escritura

---

## Licencia

Proyecto académico — TFG DAM · Xinbo Liu
