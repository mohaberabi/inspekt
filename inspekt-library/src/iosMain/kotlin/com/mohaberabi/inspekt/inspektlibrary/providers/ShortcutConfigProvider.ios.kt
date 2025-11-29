package com.mohaberabi.inspekt.inspektlibrary.providers

import com.mohaberabi.inspekt.shortcut.AppShortcutConfig
import platform.UIKit.UIApplicationShortcutIcon

internal actual class ShortcutConfigProvider {
    actual fun provideInspektShortcutConfig(): AppShortcutConfig {
        return AppShortcutConfig(
            userInfo = mapOf(),
            icon = UIApplicationShortcutIcon.iconWithSystemImageName("paperplane.fill")
        )
    }
}