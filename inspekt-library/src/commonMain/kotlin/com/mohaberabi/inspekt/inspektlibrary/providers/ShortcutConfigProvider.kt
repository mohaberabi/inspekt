package com.mohaberabi.inspekt.inspektlibrary.providers

import com.mohaberabi.inspekt.shortcut.AppShortcutConfig

internal expect class ShortcutConfigProvider {

    fun provideInspektShortcutConfig(): AppShortcutConfig
}