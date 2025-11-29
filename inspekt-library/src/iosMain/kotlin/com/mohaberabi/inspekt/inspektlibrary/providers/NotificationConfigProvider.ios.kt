package com.mohaberabi.inspekt.inspektlibrary.providers

import com.mohaberabi.inspekt.notification.NotificationConfig

internal actual class NotificationConfigProvider {
    actual fun provideInspektNotificationConfig(): NotificationConfig {
        return NotificationConfig(
            userInfo = mapOf("inspekt" to true)
        )
    }
}