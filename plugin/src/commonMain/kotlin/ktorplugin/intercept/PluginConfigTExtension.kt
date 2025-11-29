package ktorplugin.intercept

import ktorplugin.InspektConfig
import ktorplugin.utils.CallIdKey
import ktorplugin.utils.CallStartMillisKey
import ktorplugin.utils.EntryKey
import ktorplugin.utils.generateCallId
import ktorplugin.utils.toLogEntry
import io.ktor.client.plugins.api.ClientPluginBuilder
import io.ktor.http.content.OutgoingContent
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.launch
import ktorplugin.contentmapper.decodeReadable
import ktorplugin.utils.extractContentType


internal fun ClientPluginBuilder<InspektConfig>.onInspektRequest() {
    val inspekt = pluginConfig.inspekt
    val scope = pluginConfig.scope
    val requestDecoder = pluginConfig.requestBodyDecoder
    onRequest { request, _ ->
        val time = getTimeMillis()
        val callId = generateCallId(time = time, value = request.hashCode())
        val entry = request.toLogEntry(callId = callId, millis = time)
        val rawBytes = request.body.extractRawContent()
        val decodedBody: String = requestDecoder
            .decode(entry, rawBytes)
            ?: rawBytes.decodeReadable(request.headers.extractContentType())
        val newEntry = entry.copy(requestBody = decodedBody)
        request.attributes.put(CallIdKey, callId)
        request.attributes.put(CallStartMillisKey, time)
        request.attributes.put(EntryKey, newEntry)
        scope.launch { inspekt.logCall(newEntry) }
    }
}

internal fun Any?.extractRawContent() = when (this) {
    null -> byteArrayOf()
    is String -> encodeToByteArray()
    is ByteArray -> this
    is OutgoingContent.ByteArrayContent -> this.bytes()
    is OutgoingContent.ReadChannelContent ->
        "<streaming body>".encodeToByteArray()

    else -> this.toString().encodeToByteArray()
}