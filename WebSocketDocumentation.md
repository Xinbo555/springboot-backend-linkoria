# 📡 Documentación de WebSockets (STOMP) - Módulo de Mensajería

Esta sección detalla la comunicación en tiempo real para la gestión de mensajes. El sistema utiliza STOMP sobre SockJS.

---

## ⚙️ Configuración de Conexión

- **Endpoint de conexión:** `/ws`
- **Prefijo de destino (App):** `/app`
- **Broker de suscripción (Tópicos):** `/topic`

---

## 📥 Suscripciones (Client Side)

Para recibir actualizaciones en tiempo real, el cliente debe suscribirse al tópico de la conversación específica.

| Tópico | Descripción |
|--------|-------------|
| `/topic/conversation/{conversationId}` | Recibe notificaciones de nuevos mensajes, ediciones y eliminaciones. |

---

## 📤 Comandos: Enviar Datos (Inbound)

Mensajes que el cliente envía al servidor a través de la conexión WebSocket.

### 1. Enviar Nuevo Mensaje

- **Ruta:** `/app/message/send/{conversationId}`
- **Payload:**

```json
{
  "content": "Contenido del mensaje",
  "messageType": "TEXT",
  "replyToMessageId": null
}
```

**Parámetros:**
- `content` (string, requerido): Contenido del mensaje
- `messageType` (string, opcional): Tipo de mensaje - `TEXT`, `IMAGE`, `FILE`, `VIDEO`, `AUDIO`. Default: `TEXT`
- `replyToMessageId` (long, opcional): ID del mensaje al que se responde para crear threads

---

### 2. Editar Mensaje

- **Ruta:** `/app/message/edit/{conversationId}`
- **Payload:**

```json
{
  "messageId": 123,
  "newContent": "Contenido actualizado"
}
```

**Parámetros:**
- `messageId` (long, requerido): ID del mensaje a editar
- `newContent` (string, requerido): Nuevo contenido del mensaje

**Restricciones:**
- Solo el autor del mensaje puede editarlo
- Solo mensajes de tipo `TEXT` pueden ser editados

---

### 3. Eliminar Mensaje

- **Ruta:** `/app/message/delete/{conversationId}`
- **Payload:**

```json
{
  "messageId": 123
}
```

**Parámetros:**
- `messageId` (long, requerido): ID del mensaje a eliminar

**Restricciones:**
- Solo el autor del mensaje puede eliminarlo
- La operación es irreversible (hard-delete)

---

## 🔔 Eventos: Recibir Datos (Outbound)

Formato de los mensajes que el servidor envía a los clientes suscritos al tópico. Todos los mensajes llegan envueltos en una estructura común (`WebSocketNotification`).

### Estructura General (Wrapper)

```json
{
  "type": "STRING",
  "payload": "OBJECT",
  "timestamp": 1714156123456
}
```

**Campos:**
- `type` (string): Tipo de evento (`MESSAGE_CREATED`, `MESSAGE_EDITED`, `MESSAGE_DELETED`)
- `payload` (object): Datos específicos según el tipo de evento
- `timestamp` (long): Marca de tiempo en milisegundos

---

### A. Nuevo Mensaje (`type: "MESSAGE_CREATED"`)

```json
{
  "type": "MESSAGE_CREATED",
  "payload": {
    "messageId": 1,
    "conversationId": 10,
    "userId": "uuid-v4",
    "content": "Hola mundo",
    "messageType": "TEXT",
    "replyToMessageId": null,
    "isEdited": false,
    "isReply": false,
    "createdAt": "2024-04-27T10:00:00Z",
    "updatedAt": "2024-04-27T10:00:00Z"
  },
  "timestamp": 1714156123456
}
```

**Campos del payload:**
- `messageId` (long): ID único del mensaje
- `conversationId` (long): ID de la conversación
- `userId` (UUID): ID del usuario autor
- `content` (string): Contenido del mensaje
- `messageType` (string): Tipo de mensaje (`TEXT`, `IMAGE`, `FILE`, `VIDEO`, `AUDIO`)
- `replyToMessageId` (long): ID del mensaje al que responde (null si no es respuesta)
- `isEdited` (boolean): Indica si ha sido editado
- `isReply` (boolean): Indica si es respuesta a otro mensaje
- `createdAt` (ISO-8601): Fecha de creación
- `updatedAt` (ISO-8601): Fecha de última actualización

---

### B. Mensaje Editado (`type: "MESSAGE_EDITED"`)

```json
{
  "type": "MESSAGE_EDITED",
  "payload": {
    "messageId": 1,
    "conversationId": 10,
    "userId": "uuid-v4",
    "content": "Contenido corregido",
    "isEdited": true,
    "updatedAt": "2024-04-27T10:05:00Z"
  },
  "timestamp": 1714156125789
}
```

**Campos del payload:**
- `messageId` (long): ID del mensaje editado
- `conversationId` (long): ID de la conversación
- `userId` (UUID): ID del usuario que editó
- `content` (string): Nuevo contenido
- `isEdited` (boolean): Siempre true
- `updatedAt` (ISO-8601): Fecha de edición

---

### C. Mensaje Eliminado (`type: "MESSAGE_DELETED"`)

```json
{
  "type": "MESSAGE_DELETED",
  "payload": {
    "messageId": 1,
    "conversationId": 10,
    "deleted": true
  },
  "timestamp": 1714156128901
}
```

**Campos del payload:**
- `messageId` (long): ID del mensaje eliminado
- `conversationId` (long): ID de la conversación
- `deleted` (boolean): Siempre true

---

## 🔐 Autenticación

La conexión WebSocket requiere autenticación JWT válida en el header `Authorization`.

```
Authorization: Bearer <JWT_TOKEN>
```

El usuario autenticado debe ser participante de la conversación para:
- Enviar mensajes
- Editar sus propios mensajes
- Eliminar sus propios mensajes

---

# 🔌 Ejemplos de Cliente WebSocket - Kotlin y JavaScript

Ejemplos completos de cómo conectarse y realizar operaciones con el WebSocket STOMP en ambos lenguajes.

---

## 1️⃣ Conectar y Suscribirse

### JavaScript (con SockJS + Stomp)

```javascript
// Importar librerías (desde CDN o npm)
// <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
// <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

class WebSocketManager {
    constructor(jwtToken, conversationId) {
        this.jwtToken = jwtToken;
        this.conversationId = conversationId;
        this.stompClient = null;
    }

    connect() {
        const socket = new SockJS('/ws');
        this.stompClient = Stomp.over(socket);

        // Conectar con autenticación JWT
        this.stompClient.connect(
            { 'Authorization': 'Bearer ' + this.jwtToken },
            (frame) => {
                console.log('✅ Conectado a WebSocket');
                this.subscribe();
            },
            (error) => {
                console.error('❌ Error conectando:', error);
            }
        );
    }

    subscribe() {
        this.stompClient.subscribe(
            `/topic/conversation/${this.conversationId}`,
            (message) => {
                const notification = JSON.parse(message.body);
                this.handleNotification(notification);
            }
        );
    }

    handleNotification(notification) {
        console.log('📨 Evento recibido:', notification.type);

        switch (notification.type) {
            case 'MESSAGE_CREATED':
                this.onMessageCreated(notification.payload);
                break;
            case 'MESSAGE_EDITED':
                this.onMessageEdited(notification.payload);
                break;
            case 'MESSAGE_DELETED':
                this.onMessageDeleted(notification.payload);
                break;
            default:
                console.warn('Tipo de evento desconocido:', notification.type);
        }
    }

    onMessageCreated(message) {
        // Agregar nuevo mensaje a la UI
        console.log('🆕 Nuevo mensaje:', message.content);
        // this.addMessageToUI(message);
    }

    onMessageEdited(message) {
        // Actualizar mensaje existente
        console.log('✏️ Mensaje editado:', message.messageId);
        // this.updateMessageInUI(message);
    }

    onMessageDeleted(message) {
        // Remover mensaje de la UI
        console.log('🗑️ Mensaje eliminado:', message.messageId);
        // this.removeMessageFromUI(message.messageId);
    }

    disconnect() {
        if (this.stompClient) {
            this.stompClient.disconnect(() => {
                console.log('Desconectado de WebSocket');
            });
        }
    }
}

// Uso
const manager = new WebSocketManager('tu_jwt_token_aqui', 123);
manager.connect();

// Desconectar cuando no lo necesites
// manager.disconnect();
```

### Kotlin (con OkHttp WebSocket)

```kotlin
import okhttp3.*
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.concurrent.TimeUnit

class WebSocketManager(
    private val jwtToken: String,
    private val conversationId: Long,
    private val baseUrl: String = "ws://localhost:8080"
) {
    private var webSocket: WebSocket? = null
    private val httpClient = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private val objectMapper = ObjectMapper()
    private var messageId = 0

    fun connect() {
        val request = Request.Builder()
            .url("$baseUrl/ws")
            .addHeader("Authorization", "Bearer $jwtToken")
            .build()

        webSocket = httpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("✅ Conectado a WebSocket")
                // Suscribirse al tópico (STOMP SUBSCRIBE frame)
                subscribe()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val notification = objectMapper.readTree(text)
                    val type = notification.get("type").asText()
                    val payload = notification.get("payload")

                    when (type) {
                        "MESSAGE_CREATED" -> onMessageCreated(payload)
                        "MESSAGE_EDITED" -> onMessageEdited(payload)
                        "MESSAGE_DELETED" -> onMessageDeleted(payload)
                        else -> println("⚠️ Tipo desconocido: $type")
                    }
                } catch (e: Exception) {
                    println("❌ Error procesando mensaje: ${e.message}")
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                println("❌ Error WebSocket: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("🔌 WebSocket cerrado: $reason")
            }
        })
    }

    private fun subscribe() {
        val stompFrame = """
            SUBSCRIBE
            id:sub-0
            destination:/topic/conversation/$conversationId
            
        """.trimIndent()

        webSocket?.send(stompFrame)
    }

    private fun onMessageCreated(payload: Any) {
        println("🆕 Nuevo mensaje: ${payload}")
    }

    private fun onMessageEdited(payload: Any) {
        println("✏️ Mensaje editado: ${payload}")
    }

    private fun onMessageDeleted(payload: Any) {
        println("🗑️ Mensaje eliminado: ${payload}")
    }

    fun disconnect() {
        webSocket?.close(1000, "Desconexión normal")
    }
}

// Uso
val manager = WebSocketManager("tu_jwt_token_aqui", 123L)
manager.connect()

// Desconectar
// manager.disconnect()
```

---

## 2️⃣ Enviar Nuevo Mensajeg

### JavaScript

```javascript
// Usando la clase WebSocketManager anterior
manager.sendMessage = function(content, messageType = 'TEXT', replyToMessageId = null) {
    const message = {
        content: content,
        messageType: messageType,
        replyToMessageId: replyToMessageId
    };

    this.stompClient.send(
        `/app/message/send/${this.conversationId}`,
        {},
        JSON.stringify(message)
    );

    console.log('📤 Mensaje enviado:', content);
};

// Uso
manager.sendMessage('Hola mundo', 'TEXT', null);
manager.sendMessage('Respondiendo a un mensaje', 'TEXT', 123); // replyToMessageId = 123
```

### Kotlin

```kotlin
fun sendMessage(content: String, messageType: String = "TEXT", replyToMessageId: Long? = null) {
    val message = mapOf(
        "content" to content,
        "messageType" to messageType,
        "replyToMessageId" to replyToMessageId
    )

    val stompFrame = """
        SEND
        destination:/app/message/send/$conversationId
        content-length:${objectMapper.writeValueAsString(message).length}
        
        ${objectMapper.writeValueAsString(message)}
    """.trimIndent()

    webSocket?.send(stompFrame)
    println("📤 Mensaje enviado: $content")
}

// Uso
manager.sendMessage("Hola mundo", "TEXT", null)
manager.sendMessage("Respondiendo a un mensaje", "TEXT", 123L)
```

---

## 3️⃣ Editar Mensaje

### JavaScript

```javascript
manager.editMessage = function(messageId, newContent) {
    const message = {
        messageId: messageId,
        newContent: newContent
    };

    this.stompClient.send(
        `/app/message/edit/${this.conversationId}`,
        {},
        JSON.stringify(message)
    );

    console.log('✏️ Mensaje editado:', messageId);
};

// Uso
manager.editMessage(456, 'Contenido actualizado');
```

### Kotlin

```kotlin
fun editMessage(messageId: Long, newContent: String) {
    val message = mapOf(
        "messageId" to messageId,
        "newContent" to newContent
    )

    val payload = objectMapper.writeValueAsString(message)
    val stompFrame = """
        SEND
        destination:/app/message/edit/$conversationId
        content-length:${payload.length}
        
        $payload
    """.trimIndent()

    webSocket?.send(stompFrame)
    println("✏️ Mensaje editado: $messageId")
}

// Uso
manager.editMessage(456L, "Contenido actualizado")
```

---

## 4️⃣ Eliminar Mensaje

### JavaScript

```javascript
manager.deleteMessage = function(messageId) {
    const message = {
        messageId: messageId
    };

    this.stompClient.send(
        `/app/message/delete/${this.conversationId}`,
        {},
        JSON.stringify(message)
    );

    console.log('🗑️ Mensaje eliminado:', messageId);
};

// Uso
manager.deleteMessage(789);
```

### Kotlin

```kotlin
fun deleteMessage(messageId: Long) {
    val message = mapOf(
        "messageId" to messageId
    )

    val payload = objectMapper.writeValueAsString(message)
    val stompFrame = """
        SEND
        destination:/app/message/delete/$conversationId
        content-length:${payload.length}
        
        $payload
    """.trimIndent()

    webSocket?.send(stompFrame)
    println("🗑️ Mensaje eliminado: $messageId")
}

// Uso
manager.deleteMessage(789L)
```

---

## ⚠️ Notas Importantes

1. **Reemplazar `tu_jwt_token_aqui`** con el token JWT válido del usuario
2. **Reemplazar `localhost:8080`** con la URL del servidor
3. **Kotlin con STOMP:** Los frames STOMP deben terminar con `\n\n` (línea en blanco doble)
4. **JavaScript:** Usa `stompClient.connect()` que maneja todo automáticamente
5. **Manejo de errores:** Agregar try-catch y reintentos en producción
6. **Desconexión:** Siempre desconectar cuando no se necesite para liberar recursos
