package com.mohaberabi.inspekt

import platform.UserNotifications.UNNotification
import platform.UserNotifications.UNNotificationPresentationOptionAlert
import platform.UserNotifications.UNNotificationPresentationOptionSound
import platform.UserNotifications.UNNotificationPresentationOptions
import platform.UserNotifications.UNNotificationResponse
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNUserNotificationCenterDelegateProtocol
import platform.darwin.NSObject

class UNNotificationDelegate(
    private val onReceivedNotification: (UNNotification) -> Unit = {},
    private val onNotificationClicked: (UNNotification) -> Unit = {}
) : NSObject(),
    UNUserNotificationCenterDelegateProtocol {

    override fun userNotificationCenter(
        center: UNUserNotificationCenter,
        willPresentNotification: UNNotification,
        withCompletionHandler: (UNNotificationPresentationOptions) -> Unit
    ) {
        onReceivedNotification(willPresentNotification)
        withCompletionHandler(
            UNNotificationPresentationOptionAlert or
                    UNNotificationPresentationOptionSound
        )
    }

    override fun userNotificationCenter(
        center: UNUserNotificationCenter,
        didReceiveNotificationResponse: UNNotificationResponse,
        withCompletionHandler: () -> Unit
    ) {
        val notification = didReceiveNotificationResponse.notification
        onNotificationClicked(notification)
        withCompletionHandler()
    }
}