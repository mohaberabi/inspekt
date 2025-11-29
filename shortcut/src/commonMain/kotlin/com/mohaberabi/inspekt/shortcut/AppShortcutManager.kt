package com.mohaberabi.inspekt.shortcut

interface AppShortcutManager {
    fun addShortcut(shortcut: AppShortcut)
    fun removeShortcutById(id: String)
}