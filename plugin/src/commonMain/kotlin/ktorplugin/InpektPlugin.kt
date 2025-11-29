package ktorplugin

import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.GlobalInspekt
import ktorplugin.intercept.inspektReceive
import ktorplugin.intercept.onInspektRequest
import ktorplugin.utils.NAME
import io.ktor.client.plugins.api.createClientPlugin

fun InspektPlugin() = createClientPlugin(
    name = NAME,
    createConfiguration = {
        InspektConfig(
            inspekt = GlobalInspekt.sharedInstance,
        )
    }
) {
    val inspekt = pluginConfig.inspekt
    val scope = pluginConfig.scope
    val responseDecoder = pluginConfig.responseBodyDecoder
    onInspektRequest()
    client.inspektReceive(scope = scope, inspekt = inspekt, responseDecoder = responseDecoder)
}



