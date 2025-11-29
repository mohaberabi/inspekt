package ktorplugin

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
internal val inspektJson = Json {
    prettyPrint = true
    encodeDefaults = true
    explicitNulls = false
    ignoreUnknownKeys = true
    prettyPrintIndent = "  "
}