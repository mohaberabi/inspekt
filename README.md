# 🕵️‍♂️ Inspekt

### The KMP-native HTTP Inspector for Ktor — elegant, minimal, and actually useful.

**Inspekt** gives you full visibility into your **Ktor** network calls on **Android** and **iOS**.

- Real‑time HTTP logging
- Pretty JSON viewing
- Request/response size, SSL flag, metadata
- In‑app inspector UI (Compose)
- Notifications
- App Shortcuts
- Room KMP persistence
- Optional body decoders (for encrypted/protobuf bodies)

It feels like Chucker — but built *properly* for Kotlin Multiplatform.

# ⚙️ Setup (Android + iOS)

Call:

```kotlin
GlobalInspekt.configure(InspektConfig(/* Android: pass context */))
/* or even use the no implementation instance in case u don't want to ship it */
```

Then install the plugin:

```kotlin
val client = HttpClient {
    install(InspektPlugin())
}
```

Done.  
Every request and response is now captured.

---

# 📡 What Inspekt Logs

### Request:

- URL
- Endpoint
- Method
- Headers
- Content‑Type / Charset
- SSL detection
- Body (decoded or raw)
- Size
- Timestamp

### Response:

- Status code
- Headers
- Body
- Duration
- Size
- Content‑Type
- Errors

All stored in Room KMP automatically.

---

# 🔐 Optional: Body Decoding (Encrypted / Protobuf / Custom)

You can decode them like:

```kotlin
InspektConfig(
    requestBodyDecoder = { entry, raw ->
        myDecrypter.decrypt(raw)
    },
    responseBodyDecoder = { entry, raw ->
        myProtobufParser.parse(raw)
    }
)
```

# 🕰 Real‑Time Notifications

Every logged call sends a notification (configurable).  
Tap → inspector UI opens.

### Android

Uses NotificationManager + PendingIntent.

### iOS

Uses UNUserNotificationCenter and a floating UIWindow.

You can customize the payload via:

```kotlin
NotificationConfigProvider {
    NotificationConfig(userInfo = mapOf("inspekt" to true))
}
```

---

# ⚡ Shortcuts (Quick Launch)

### Android

Inspekt adds:

- Dynamic Shortcut

Shortcut opens the Inspector Activity.

### iOS

Inspekt adds an App Shortcut:  
Tap → opens the floating inspector window.

Zero Swift.

---

# 🗂 Storage — Powered by Room KMP

All entries are saved automatically:

- can be toggled inside configuration when initialized
- Works offline

---

# 🖥 Inspector UI (Compose Multiplatform)

One UI for both platforms.

### Android

A standalone Activity (`InspektActivity`).

### iOS

A separate `UIWindow` on top of your app  
(movable, dismissible, non-invasive).

### Includes:

- List of calls
- Detail screen
- Pretty JSON
- Copy / Share options
- Sizes, timing, headers
- Status color coding

---

# 🔌 Ktor Integration — Clean & Efficient

Inspekt hooks into:

### On Request

Captures:

- URL
- Headers
- Metadata
- Body (via raw extraction)
- Timestamp

### On Response

Reads the channel **once**, clones it, then logs and forwards it safely.

Works with all content types and large bodies.

---

# 🔍 How Request Bodies Are Captured

Inspekt reads raw body content via:

- `String`
- `ByteArray`
- `OutgoingContent.*`
- Fallback to `.toString()`

Streaming bodies produce `" <streaming body> "`  
unless you decode them manually.

---

# 🔍 How Response Bodies Are Captured

Inspekt uses:

```kotlin
val bytes = channel.readRemaining().readByteArray()
val newCall = call.replaceResponse { ByteReadChannel(bytes) }
```

So:

- Body is logged
- Body is still readable by the app
- No double‑receive problem

---

