package com.mohaberabi.inspekt.inspektlibrary.providers

import com.mohaberabi.inspekt.notification.NotificationConfig

internal expect class NotificationConfigProvider {

    fun provideInspektNotificationConfig(): NotificationConfig
}