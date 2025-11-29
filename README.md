# 📡 Inspekt

### A cross-platform HTTP inspector for Kotlin Multiplatform — like Chucker, but for Ktor.

**Inspekt** logs, decodes, stores, and visualizes every Ktor HTTP request & response on **Android**
and **iOS**.  
It requires **no Swift**, **no extra setup**, and includes a full inspector UI built with **Compose
Multiplatform**.

---

## 🚀 Features

### ✅ Full Ktor HTTP Logging

Inspekt automatically logs:

- Request & response bodies
- Headers
- Status code
- Duration
- Endpoint & method
- Content type, charset, content length
- Request/response size (bytes)
- SSL detection
- Pretty printed JSON
- Custom body decoding (encrypted/protobuf/etc.)

---

### 🖥 In-App Inspector UI (Android & iOS)

#### **Android**

- Opens a dedicated `InspektActivity`
- Accessible via:
    - Notification tap
    - Dynamic shortcuts
    - Launcher shortcut

#### **iOS**

- Opens as a separate floating `UIWindow`
- Activated via:
    - Notification click
    - App Shortcut
- Implemented 100% in Kotlin — **no Swift required**

---

### 🔔 Real-Time Notifications

Each HTTP call can trigger a configurable notification.

```kotlin
notificationManager.showLocalNotification(
    title = entry.url,
    body = "Logged call: ${entry.statusCode}",
    id = entry.id.hashCode(),
    config = NotificationConfig(...
)
)
```

---

### 📦 KMP Room Database

Every request and response is persisted with **Room KMP**, enabling:

- Complete offline history
- Paging support
- Searching/filtering (optional)
- Custom retention policies

---

### 🧩 Pluggable Body Decoders

Decode encrypted, protobuf, or custom formats:

```kotlin
InspektConfig(
    requestBodyDecoder = { entry, rawBytes ->
        myDecrypter.decode(rawBytes)
    },
    responseBodyDecoder = { entry, rawBytes ->
        myProtobufParser.parse(rawBytes)
    }
)
```

If the decoder returns `null`, Inspekt falls back to:

- Pretty JSON
- Plain text
- `<streaming body>` fallback

---

## 🛠 Setup

### 1. Configure Inspekt

```kotlin
GlobalInspekt.configure(
    InspektConfig(
        // Android: pass context
        // iOS: no args
    )
)
```

Must be called **once** on startup.

---

### 2. Install Ktor Plugin

```kotlin
val client = HttpClient {
    install(InspektPlugin())
}
```

That's it. All calls are logged.

---

## 🔍 Using the Inspector UI

### Android

```kotlin
context.startActivity(Intent(context, InspektActivity::class.java))
```

### iOS

```kotlin
InspektViewControllerPresenter.show()
```

---

## 🧱 Architecture Overview

```
┌──────────────────────────────────────────────┐
│                 InspektPlugin                │
│   (Ktor request/response interceptor)        │
└──────────────────────────────────────────────┘
               │                │
               ▼                ▼
      Extract request       Extract response
        + raw bytes            + raw bytes
               │                │
               └─────── Decode via user  ───────┐
                       (optional)               │
                                                ▼
                                          PreProcessing
                                                ▼
                                       Persist in Room KMP
                                                ▼
                                        Notify via manager
                                                ▼
                                       View in Compose UI
```

---
