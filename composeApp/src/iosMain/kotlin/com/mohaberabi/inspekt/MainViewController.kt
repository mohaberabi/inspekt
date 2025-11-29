package com.mohaberabi.inspekt

import androidx.compose.ui.window.ComposeUIViewController
import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.GlobalInspekt
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfig
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfigStrategy
import platform.UIKit.UIApplication
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter

fun MainViewController() = ComposeUIViewController(
    configure = {
        val strategy = InspektConfigStrategy.Actual(
            config = InspektConfig(),
            notificationEnabled = true,
            shortcutEnabled = true,
            maxPersistenceEntry = 50
        )
        GlobalInspekt.configure(
            strategy = strategy
        )
        requestNotificationPermission()
        setupDelegates()
    }
) { App() }

private val rootAppDelegate = RootAppDelegate(
    onItemOpenedApp = GlobalInspekt::shortcutOpenedApp
)

private val notificationDelegate = UNNotificationDelegate(
    onNotificationClicked = GlobalInspekt::notificationClicked
)


private fun setupDelegates() {
    UNUserNotificationCenter.currentNotificationCenter().delegate = notificationDelegate
    UIApplication.sharedApplication.setDelegate(rootAppDelegate)
}

fun requestNotificationPermission() {
    val center = UNUserNotificationCenter.currentNotificationCenter()
    center.requestAuthorizationWithOptions(
        options = UNAuthorizationOptionAlert or
                UNAuthorizationOptionSound or
                UNAuthorizationOptionBadge
    ) { granted, _ -> ; }
}
