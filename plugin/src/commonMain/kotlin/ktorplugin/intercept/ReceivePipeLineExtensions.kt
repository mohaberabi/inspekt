package ktorplugin.intercept

import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.Inspekt
import ktorplugin.utils.CallStartMillisKey
import ktorplugin.utils.EntryKey
import io.ktor.client.HttpClient
import io.ktor.client.call.replaceResponse
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.request
import io.ktor.util.date.getTimeMillis
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readRemaining
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.io.readByteArray
import ktorplugin.contentmapper.decodeReadable
import ktorplugin.decoder.InspektBodyDecoder
import ktorplugin.utils.extractContentType
import ktorplugin.utils.toLogEntry

internal fun HttpClient.inspektReceive(
    inspekt: Inspekt,
    scope: CoroutineScope,
    responseDecoder: InspektBodyDecoder,
) {
    receivePipeline.intercept(HttpReceivePipeline.After) { response ->
        val start = response.request.attributes.getOrNull(CallStartMillisKey) ?: getTimeMillis()
        val call = response.call
        val channel = response.bodyAsChannel()
        val bytes = channel.readRemaining().readByteArray()
        val newCall = call.replaceResponse { ByteReadChannel(bytes); }
        val now = getTimeMillis()
        val duration = now - start
        val entry = call.attributes.getOrNull(EntryKey) ?: return@intercept
        val completedEntry = response.toLogEntry(
            bytesSize = bytes.size.toLong(),
            duration = duration,
            requestedEntry = entry
        )
        val decodedBody = responseDecoder.decode(completedEntry, bytes)
            ?: bytes.decodeReadable(response.headers.extractContentType())

        val newEntry = completedEntry.copy(responseBody = decodedBody)
        scope.launch { inspekt.logCall(newEntry) }
        proceedWith(newCall.response)
    }
}
