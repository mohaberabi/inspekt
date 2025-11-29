package com.mohaberabi.inspekt.notification


interface InspektNotificationManager {
    fun showLocalNotification(
        title: String,
        body: String,
        id: Int,
        config: NotificationConfig
    )

}
