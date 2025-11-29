package com.mohaberabi.inspekt.inspektlibrary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import com.mohaberabi.inspekt.inspektlibrary.utils.sizeReadable
import com.mohaberabi.inspekt.inspektlibrary.utils.timeReadable
import kotlin.Int.Companion


@Composable
internal fun ColumnScope.LogOverviewBox(
    log: HttpLogEntry,
) {

    LogOverviewItem(leading = "URL", trailing = log.url)
    LogOverviewItem(leading = "Method", trailing = log.method)
    LogOverviewItem(leading = "Protocol", trailing = log.protocol ?: "")
    LogOverviewItem(leading = "Status", trailing = log.inspektStatus.name.lowercase())
    LogOverviewItem(leading = "Response Status", trailing = log.statusCode?.toString() ?: "")
    LogOverviewItem(leading = "SSL", trailing = log.ssl?.toString() ?: "")
    LogOverviewItem(
        leading = "Request Size",
        trailing = log.requestSizeBytes.sizeReadable()
    )

    LogOverviewItem(
        leading = "Response Size",
        trailing = log.responseSizeBytes?.sizeReadable() ?: ""
    )

    LogOverviewItem(
        leading = "Total Size",
        trailing = log.totalSize().sizeReadable()
    )
    LogOverviewItem(
        leading = "Request Time",
        trailing = log.timestampMillis.toString(),
    )

    LogOverviewItem(
        leading = "Duration",
        trailing = log.durationMillis?.timeReadable() ?: ""
    )
}


@Composable
internal fun LogOverviewItem(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    leading: String,
    trailing: String,
    maxLines: Int = 1,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        Text(
            leading,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = maxLines
        )
        Text(
            trailing,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}