# Linkoria — Backend

> Trabajo de Fin de Grado · Desarrollo de Aplicaciones Multiplataforma  
> Autor: **Xinbo Liu**

---

## ¿Qué es Linkoria?

Linkoria es una aplicación de chat en tiempo real inspirada en Discord. Permite a los usuarios comunicarse mediante mensajes directos y canales dentro de servidores, gestionar amistades, reaccionar a mensajes y mucho más.

Este repositorio contiene el **backend** desarrollado con Spring Boot, siguiendo una arquitectura limpia basada en Domain-Driven Design (DDD).

---

## Tecnologías principales

- Java 21
- Spring Boot 4.x
- Spring Data JPA + Hibernate
- MySQL 8
- WebSocket (STOMP)
- JWT (autenticación)
- Springdoc OpenAPI (Swagger)

---

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

## Ejecutar el proyecto

```bash
# Clona el repositorio
git clone https://github.com/xinboliu/linkoria-backend.git

# Entra en el directorio
cd linkoria-backend

# Ejecuta con Maven
./mvnw spring-boot:run
```

La aplicación arrancará en `http://localhost:8080`.

---

## Documentación de la API

Una vez arrancada la aplicación, puedes explorar todos los endpoints disponibles en Swagger UI:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

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

## Estado del proyecto

Este proyecto está en desarrollo activo como parte del TFG. Módulos implementados hasta el momento:

- [x] `user` — Gestión de usuarios
- [x] `auth` — Autenticación JWT
- [x] `friendship` — Sistema de amistades
- [ ] `server` — Servidores, miembros y roles
- [ ] `channel` — Canales de texto dentro de servidores
- [ ] `conversation` — Mensajes directos entre usuarios
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
