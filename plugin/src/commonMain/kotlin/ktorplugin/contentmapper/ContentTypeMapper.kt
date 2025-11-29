package ktorplugin.contentmapper

import kotlinx.serialization.json.JsonElement
import ktorplugin.inspektJson


fun ByteArray?.decodeReadable(
    contentType: String?
): String {
    if (this == null || contentType == null) return "null"
    return when {
        contentType.isJson() -> {
            val json = decodeToString()
            json.prettyJsonOrNull() ?: json
        }

        contentType.isProtobuf() -> "<${size}> protobuf bytes size"
        contentType.isText() -> decodeToString()
        else -> ""
    }
}

fun String.isJson(): Boolean = contains("json", ignoreCase = true)

fun String.isProtobuf(): Boolean = contains("proto", ignoreCase = true)

fun String.isText(): Boolean =
    equals("text", ignoreCase = true)
            || isJson()
            || contains("xml", ignoreCase = true)
            || contains("html", ignoreCase = true)
            || contains("form", ignoreCase = true)


fun String.prettyJsonOrNull() = runCatching {
    val element = inspektJson.parseToJsonElement(this)
    inspektJson.encodeToString(JsonElement.serializer(), element)
}.getOrNull()