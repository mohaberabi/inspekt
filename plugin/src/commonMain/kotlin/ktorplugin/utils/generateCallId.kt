package ktorplugin.utils

import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import io.ktor.util.AttributeKey

internal val CallIdKey = AttributeKey<String>("InspektPluginCallId")
internal val CallStartMillisKey = AttributeKey<Long>("InspektPluginCallStart")
internal val EntryKey = AttributeKey<HttpLogEntry>("InspektEntry")

internal const val NAME = "Inspekt"
internal fun generateCallId(
    time: Long,
    value: Any,
): String = "${time}-${value}"