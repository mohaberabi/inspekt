package com.mohaberabi.inspekt.inspektlibrary.providers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.mohaberabi.inspekt.inspektlibrary.ui.InspektActivity
import com.mohaberabi.inspekt.inspektlibrary.applyInspekt
import com.mohaberabi.inspekt.inspektlibrary.getAppName
import com.mohaberabi.inspekt.notification.NotificationConfig

internal actual class NotificationConfigProvider(
    private val context: Context
) {
    actual fun provideInspektNotificationConfig(): NotificationConfig {
        val intent = Intent(context, InspektActivity::class.java)
            .applyInspekt(context.getAppName())
        val pendingIntent = PendingIntent.getActivity(
            context,
            201098,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationConfig(contentPendingIntent = pendingIntent)
    }
}