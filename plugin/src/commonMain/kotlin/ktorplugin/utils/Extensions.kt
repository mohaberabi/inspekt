package ktorplugin.utils

import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import com.inspekt.modelhub.httplogenetry.InspektStatus
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.contentLength
import io.ktor.http.isSecure

internal fun HttpRequestBuilder.toLogEntry(
    callId: String,
    millis: Long,
    duration: Long = 1,
    requestBody: String? = null,
) = HttpLogEntry(
    endPoint = url.pathSegments.joinToString("/"),
    id = callId,
    ssl = url.protocol.isSecure(),
    host = url.host,
    protocol = url.protocol.name,
    timestampMillis = millis,
    method = method.value,
    url = url.toString(),
    requestHeaders = headers.entries()
        .associate { it.key to it.value.joinToString() },
    responseHeaders = mapOf(),
    requestBody = requestBody,
    durationMillis = duration,
    requestSizeBytes = calculateRequestSize(),
    inspektStatus = InspektStatus.RequestSent,
    contentLength = contentLength(),
    contentType = headers.extractContentType(),
    acceptCharset = headers.extractCharset(),
)

fun HttpResponse.toLogEntry(
    bytesSize: Long,
    duration: Long,
    requestedEntry: HttpLogEntry
): HttpLogEntry {
    return requestedEntry.copy(
        durationMillis = duration,
        responseHeaders = headers.entries()
            .associate { it.key to it.value.joinToString() },
        statusCode = status.value,
        responseSizeBytes = bytesSize,
        inspektStatus = InspektStatus.Completed,
        contentLength = contentLength()
    )
}

internal fun HeadersBuilder.extractContentType() = this[HttpHeaders.ContentType]?.lowercase() ?: ""
internal fun Headers.extractContentType() = this[HttpHeaders.ContentType]?.lowercase() ?: ""

internal fun HeadersBuilder.extractCharset() = this[HttpHeaders.AcceptCharset]?.lowercase() ?: ""