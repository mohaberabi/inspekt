package ktorplugin

import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.Inspekt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import ktorplugin.decoder.DefaultInspektBodyDecoder
import ktorplugin.decoder.InspektBodyDecoder

data class InspektConfig(
    val inspekt: Inspekt,
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    val requestBodyDecoder: InspektBodyDecoder = DefaultInspektBodyDecoder,
    val responseBodyDecoder: InspektBodyDecoder = DefaultInspektBodyDecoder
)
