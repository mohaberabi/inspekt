package com.inspekt.modelhub.httplogenetry

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class InspektStatus {
    RequestSent,
    Completed,
    Unknown,
    Failure,

}

@Serializable
data class HttpLogEntry(
    @SerialName("id") val id: String,
    @SerialName("timestamp_millis") val timestampMillis: Long,
    @SerialName("response_millis") val responseTimeStampMillis: Long? = null,
    @SerialName("method") val method: String,
    @SerialName("url") val url: String,
    @SerialName("status_code") val statusCode: Int? = null,
    @SerialName("request_headers") val requestHeaders: Map<String, String>,
    @SerialName("response_headers") val responseHeaders: Map<String, String>,
    @SerialName("request_body") val requestBody: String? = null,
    @SerialName("response_body") val responseBody: String? = null,
    @SerialName("duration_millis") val durationMillis: Long?,
    @SerialName("error") val error: String? = null,
    @SerialName("request_size_bytes") val requestSizeBytes: Long = 0L,
    @SerialName("response_size_bytes") val responseSizeBytes: Long? = null,
    @SerialName("protocol") val protocol: String? = null,
    @SerialName("ssl") val ssl: Boolean? = null,
    @SerialName("inspekt_status") val inspektStatus: InspektStatus = InspektStatus.Unknown,
    @SerialName("host") val host: String? = null,
    @SerialName("path") val endPoint: String? = null,
    @SerialName("content_type") val contentType: String? = null,
    @SerialName("accept_charset") val acceptCharset: String? = null,
    @SerialName("content_length") val contentLength: Long? = null,
)
