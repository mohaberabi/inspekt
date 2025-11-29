package com.mohaberabi.inspekt.inspektlibrary.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.mohaberabi.inspekt.inspektlibrary.ui.navigation.InspektNavGraph
import com.mohaberabi.inspekt.inspektlibrary.ui.screen.InspektScreen
import platform.Foundation.NSBundle


internal fun InspektViewController(
    onDismiss: () -> Unit = {},
) = ComposeUIViewController {
    InspektNavGraph(
        onDismiss = onDismiss,
        rootAppName = getAppName()
    )
}

internal fun getAppName(): String {
    val infoDict = NSBundle.mainBundle.infoDictionary
    val displayName = infoDict?.get("CFBundleDisplayName") as? String
    val bundleName = infoDict?.get("CFBundleName") as? String
    return displayName ?: bundleName ?: ""
}