package ktorplugin.utils

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.content.ByteArrayContent
import io.ktor.content.TextContent


fun HttpRequestBuilder.calculateRequestSize(): Long {
    return when (val content = this.body) {
        is ByteArrayContent -> content.bytes().size.toLong()
        is TextContent -> content.text.encodeToByteArray().size.toLong()
        is FormDataContent -> content.formData.toString().encodeToByteArray().size.toLong()
        is MultiPartFormDataContent -> content.contentLength ?: 0L
        else -> body.toString().encodeToByteArray().size.toLong()
    }
}