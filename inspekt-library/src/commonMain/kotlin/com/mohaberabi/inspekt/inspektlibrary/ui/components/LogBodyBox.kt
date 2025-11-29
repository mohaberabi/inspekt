package com.mohaberabi.inspekt.inspektlibrary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.inspekt.modelhub.httplogenetry.HttpLogEntry


@Composable
internal fun ColumnScope.LogBodyBox(
    log: HttpLogEntry,
    tab: LogTab
) {
    val headers = when (tab) {
        LogTab.Overview -> mapOf()
        LogTab.Request -> log.requestHeaders
        LogTab.Response -> log.responseHeaders
    }

    headers.forEach { (key, value) ->
        LogOverviewItem(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            leading = key,
            trailing = value,
        )
    }
    when (tab) {
        LogTab.Overview -> {}
        LogTab.Request -> RequestBodyBox(log = log, logTab = tab)
        LogTab.Response -> RequestBodyBox(log = log, logTab = tab)
    }
}

@Composable
private fun ColumnScope.RequestBodyBox(
    log: HttpLogEntry,
    logTab: LogTab
) {

    val body = when (logTab) {
        LogTab.Overview -> null
        LogTab.Request -> log.requestBody
        LogTab.Response -> log.responseBody
    }
    LogOverviewItem(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        leading = "Accept-Charset",
        trailing = log.acceptCharset ?: "",
        maxLines = 1,
    )
    LogOverviewItem(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        leading = "Agent",
        trailing = "Ktor/Client",
        maxLines = 1,
    )
    LogOverviewItem(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        leading = "Content-Type",
        trailing = log.contentType ?: "",
        maxLines = 1,
    )
    LogOverviewItem(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        leading = "Content-Length",
        trailing = log.contentLength?.toString() ?: "",
        maxLines = 1,
    )


    if (logTab != LogTab.Overview && body?.isNotBlank() == true) {
        SelectionContainer {
            Box(
                Modifier.fillMaxWidth().background(Color.Black),
            ) {
                Text(
                    body,
                    color = Color.Green,
                    modifier = Modifier.padding(12.dp)
                )

            }
        }

    }
}
