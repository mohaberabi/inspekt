package com.mohaberabi.inspekt.shortcut

import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationShortcutItem
import platform.UIKit.UIViewController
import platform.UIKit.shortcutItems


class IosShortcutManager() : AppShortcutManager {
    override fun addShortcut(shortcut: AppShortcut) {
        val app = UIApplication.sharedApplication
        val shortcuts = app.shortcutItems.orEmpty()
        val exists = shortcuts.any {
            val item = it as? UIApplicationShortcutItem ?: return@any false
            if (item.type == shortcut.id) return@any true
            false
        }
        if (exists) return
        val newShortcuts = mutableListOf<Any?>()
        newShortcuts.addAll(shortcuts)
        val shortcut = UIApplicationShortcutItem(
            type = shortcut.id,
            localizedTitle = shortcut.shortName,
            localizedSubtitle = shortcut.longName,
            userInfo = shortcut.config.userInfo,
            icon = shortcut.config.icon,
        )
        newShortcuts.add(shortcut)
        app.shortcutItems = newShortcuts
    }

    override fun removeShortcutById(id: String) {
        val app = UIApplication.sharedApplication
        if (app.shortcutItems.isNullOrEmpty()) return
        val shortcuts = app.shortcutItems.orEmpty().filter {
            if (it is UIApplicationShortcutItem) {
                it.type != id
            } else {
                true
            }
        }
        app.shortcutItems = shortcuts
    }
}