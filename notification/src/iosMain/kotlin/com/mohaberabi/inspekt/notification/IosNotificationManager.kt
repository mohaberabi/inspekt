package com.mohaberabi.inspekt.notification

import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue


class IosNotificationManager : InspektNotificationManager {

    override fun showLocalNotification(
        title: String,
        body: String,
        id: Int,
        config: NotificationConfig
    ) = dispatch_async(queue = dispatch_get_main_queue()) {
        val content = UNMutableNotificationContent()
            .apply {
                setTitle(title)
                setBody(body)
                setSound(UNNotificationSound.defaultSound())
                setUserInfo(config.userInfo)
            }

        val triggerTime = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            timeInterval = 5.0,
            repeats = false
        )

        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = "$id",
            content = content,
            trigger = triggerTime,

            )
        UNUserNotificationCenter.currentNotificationCenter()
            .addNotificationRequest(request = request)
            { error ->
                println("IosNotificationManager---${error}")
            }
    }
}
