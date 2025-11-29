package com.mohaberabi.inspekt.inspektlibrary.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import com.mohaberabi.inspekt.inspektlibrary.utils.sizeReadable
import com.mohaberabi.inspekt.inspektlibrary.utils.timeReadable


@Composable
internal fun LogListItem(
    log: HttpLogEntry,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            log.statusCode?.let {
                Text(
                    "$it",
                    color = it.httpStatusColor(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "${log.method}${log.endPoint}",
                    color = log.statusCode.httpStatusColor(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

        }

        Text(
            "${log.host}",
            style = MaterialTheme.typography.bodySmall,
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                log.totalSize().sizeReadable(),
                color = Color.Gray
            )
            log.durationMillis?.let {
                Text(
                    it.timeReadable(),
                    color = Color.Gray
                )
            }

        }

    }

}

fun Int?.httpStatusColor(): Color = when (this) {
    in 200..299 -> Color(0xFF4CAF50)
    in 300..399 -> Color(0xFFFFC107)
    in 400..599 -> Color(0xFFF44336)
    else -> Color.Black
}

fun HttpLogEntry.totalSize() = (responseSizeBytes ?: 0L) + requestSizeBytes