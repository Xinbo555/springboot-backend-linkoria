# WebSocket API

El servidor expone un endpoint STOMP sobre WebSocket.
Soporta conexión nativa WebSocket y SockJS como fallback.

- **Endpoint:** `/ws`
- **Prefijo de comandos:** `/app`
- **Broker de suscripciones:** `/topic`

---

## Autenticación

Todas las conexiones requieren un JWT válido en el header de conexión.
Authorization: Bearer <JWT_TOKEN>

---

## Estructura de eventos (Outbound)

Todos los mensajes emitidos por el servidor comparten el mismo wrapper:

```json
{
  "type": "NOMBRE_DEL_EVENTO",
  "payload": {},
  "timestamp": 1714156123456
}
```

---

## Módulos

### Mensajería

**Suscripción**

| Tópico | Descripción |
|--------|-------------|
| `/topic/conversation/{conversationId}` | Eventos en tiempo real de la conversación |

**Comandos**

| Ruta | Descripción |
|------|-------------|
| `/app/message/send/{conversationId}` | Enviar mensaje |
| `/app/message/edit/{conversationId}` | Editar mensaje |
| `/app/message/delete/{conversationId}` | Eliminar mensaje |

**Eventos emitidos**

| Type | Descripción |
|------|-------------|
| `MESSAGE_CREATED` | Nuevo mensaje en la conversación |
| `MESSAGE_EDITED` | Mensaje editado |
| `MESSAGE_DELETED` | Mensaje eliminado |

---

### Typing

**Suscripción**

| Tópico | Descripción |
|--------|-------------|
| `/topic/conversation/{conversationId}` | Mismo tópico que mensajería |

**Comandos**

| Ruta | Descripción |
|------|-------------|
| `/app/typing/{conversationId}` | Notificar estado de escritura |

**Eventos emitidos**

| Type | Descripción |
|------|-------------|
| `TYPING_START` | Un usuario ha empezado a escribir |
| `TYPING_STOP` | Un usuario ha parado de escribir (o timeout de 5s) |

---

## Futuro

### Presencia

| Tópico | Descripción |
|--------|-------------|
| `/topic/presence/{userId}` | Estado online/offline de un usuario |

### Notificaciones

| Tópico | Descripción |
|--------|-------------|
| `/topic/notification/{userId}` | Notificaciones personales del usuario |

### Receipts

| Tópico | Descripción |
|--------|-------------|
| `/topic/receipt/{conversationId}` | Confirmaciones de lectura de mensajes |