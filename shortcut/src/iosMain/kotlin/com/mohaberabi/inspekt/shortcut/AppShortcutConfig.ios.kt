package com.mohaberabi.inspekt.shortcut

import platform.UIKit.UIApplicationShortcutIcon

actual class AppShortcutConfig(
    val userInfo: Map<Any?, Any?>,
    val icon: UIApplicationShortcutIcon,
)